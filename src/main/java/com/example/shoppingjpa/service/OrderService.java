package com.example.shoppingjpa.service;

import com.example.shoppingjpa.dto.OrderItemResponse;
import com.example.shoppingjpa.dto.OrderRequest;
import com.example.shoppingjpa.dto.OrderResponse;
import com.example.shoppingjpa.mapper.CartItemMapper;
import com.example.shoppingjpa.mapper.OrderMapper;
import com.example.shoppingjpa.mapper.ProductMapper;
import com.example.shoppingjpa.vo.CartItem;
import com.example.shoppingjpa.vo.Order;
import com.example.shoppingjpa.vo.OrderItem;
import com.example.shoppingjpa.vo.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final CartItemMapper cartItemMapper;
    private final ProductMapper productMapper;

    /**
     * 장바구니에서 주문 생성
     */
    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest request) {
        // 1. 장바구니 아이템 조회
        List<CartItem> cartItems = cartItemMapper.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어있습니다.");
        }

        // 2. 총 금액 계산
        int totalAmount = 0;
        for (CartItem cartItem : cartItems) {
            Product product = productMapper.findById(cartItem.getProductId())
                    .orElseThrow(() -> new IllegalStateException("상품을 찾을 수 없습니다: " + cartItem.getProductId()));

            // 재고 확인
            if (product.getStock() < cartItem.getQuantity()) {
                throw new IllegalStateException("재고가 부족합니다: " + product.getName());
            }

            totalAmount += product.getPrice() * cartItem.getQuantity();
        }

        // 3. 주문 생성
        Order order = Order.builder()
                .userId(userId)
                .orderNumber(UUID.randomUUID().toString())
                .totalAmount(totalAmount)
                .status("PENDING")
                .paymentMethod(request.getPaymentMethod())
                .createdAt(LocalDateTime.now())
                .build();

        orderMapper.insertOrder(order);

        // 4. 주문 상품 생성 및 재고 차감
        for (CartItem cartItem : cartItems) {
            Product product = productMapper.findById(cartItem.getProductId()).get();

            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getId())
                    .productId(product.getId())
                    .productName(product.getName())
                    .productPrice(product.getPrice())
                    .quantity(cartItem.getQuantity())
                    .subtotal(product.getPrice() * cartItem.getQuantity())
                    .build();

            orderMapper.insertOrderItem(orderItem);

            // 재고 차감
            productMapper.updateStock(product.getId(), product.getStock() - cartItem.getQuantity());
        }

        // 5. 장바구니 비우기
        cartItemMapper.deleteAllByUserId(userId);

        // 6. 주문 응답 생성
        return getOrderById(order.getId());
    }

    /**
     * 결제 처리 (Mock)
     */
    @Transactional
    public OrderResponse processPayment(Long orderId) {
        Order order = orderMapper.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("주문을 찾을 수 없습니다."));

        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalStateException("이미 처리된 주문입니다.");
        }

        // Mock 결제 - 항상 성공
        orderMapper.updateOrderStatus(orderId, "PAID");

        return getOrderById(orderId);
    }

    /**
     * 주문 상세 조회
     */
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderMapper.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("주문을 찾을 수 없습니다."));

        List<OrderItem> orderItems = orderMapper.findOrderItemsByOrderId(orderId);

        List<OrderItemResponse> itemResponses = orderItems.stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .productPrice(item.getProductPrice())
                        .quantity(item.getQuantity())
                        .subtotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }

    /**
     * 주문 번호로 조회
     */
    public OrderResponse getOrderByOrderNumber(String orderNumber) {
        Order order = orderMapper.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new IllegalStateException("주문을 찾을 수 없습니다."));

        return getOrderById(order.getId());
    }

    /**
     * 사용자 주문 목록
     */
    public List<OrderResponse> getUserOrders(Long userId) {
        List<Order> orders = orderMapper.findByUserId(userId);

        return orders.stream()
                .map(order -> getOrderById(order.getId()))
                .collect(Collectors.toList());
    }
}

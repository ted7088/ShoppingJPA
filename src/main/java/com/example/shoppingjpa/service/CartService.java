package com.example.shoppingjpa.service;

import com.example.shoppingjpa.dto.CartItemRequest;
import com.example.shoppingjpa.dto.CartItemResponse;
import com.example.shoppingjpa.dto.CartSummaryResponse;
import com.example.shoppingjpa.mapper.CartItemMapper;
import com.example.shoppingjpa.mapper.ProductMapper;
import com.example.shoppingjpa.vo.CartItem;
import com.example.shoppingjpa.vo.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemMapper cartItemMapper;
    private final ProductMapper productMapper;

    /**
     * 장바구니에 상품 추가
     */
    public CartItemResponse addToCart(Long userId, CartItemRequest request) {
        // 상품 존재 및 재고 확인
        Product product = productMapper.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));

        if (product.getStock() < request.getQuantity()) {
            throw new IllegalArgumentException("재고가 부족합니다");
        }

        // 이미 장바구니에 있는지 확인
        Optional<CartItem> existing = cartItemMapper.findByUserIdAndProductId(userId, request.getProductId());

        if (existing.isPresent()) {
            // 기존 항목의 수량 증가
            CartItem cartItem = existing.get();
            int newQuantity = cartItem.getQuantity() + request.getQuantity();

            if (product.getStock() < newQuantity) {
                throw new IllegalArgumentException("재고가 부족합니다");
            }

            cartItemMapper.updateQuantity(cartItem.getId(), newQuantity);
            cartItem.setQuantity(newQuantity);

            log.info("장바구니 수량 업데이트: userId={}, productId={}, quantity={}",
                    userId, request.getProductId(), newQuantity);

            return convertToResponse(cartItem, product);
        } else {
            // 새 항목 추가
            CartItem cartItem = CartItem.builder()
                    .userId(userId)
                    .productId(request.getProductId())
                    .quantity(request.getQuantity())
                    .build();

            cartItemMapper.insertCartItem(cartItem);

            log.info("장바구니 추가: userId={}, productId={}, quantity={}",
                    userId, request.getProductId(), request.getQuantity());

            return convertToResponse(cartItem, product);
        }
    }

    /**
     * 장바구니 목록 조회
     */
    @Transactional(readOnly = true)
    public CartSummaryResponse getCartItems(Long userId) {
        List<CartItem> cartItems = cartItemMapper.findByUserId(userId);

        List<CartItemResponse> items = cartItems.stream()
                .map(cartItem -> {
                    Product product = productMapper.findById(cartItem.getProductId())
                            .orElse(null);
                    return product != null ? convertToResponse(cartItem, product) : null;
                })
                .filter(item -> item != null)
                .toList();

        int totalQuantity = items.stream()
                .mapToInt(CartItemResponse::getQuantity)
                .sum();

        int totalPrice = items.stream()
                .mapToInt(CartItemResponse::getSubtotal)
                .sum();

        return CartSummaryResponse.builder()
                .items(items)
                .totalQuantity(totalQuantity)
                .totalPrice(totalPrice)
                .build();
    }

    /**
     * 수량 변경
     */
    public CartItemResponse updateQuantity(Long userId, Long cartItemId, Integer quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("수량은 최소 1개 이상이어야 합니다");
        }

        // 장바구니 항목 조회
        CartItem cartItem = cartItemMapper.findByUserId(userId).stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다"));

        // 상품 재고 확인
        Product product = productMapper.findById(cartItem.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다");
        }

        cartItemMapper.updateQuantity(cartItemId, quantity);
        cartItem.setQuantity(quantity);

        log.info("장바구니 수량 변경: cartItemId={}, quantity={}", cartItemId, quantity);

        return convertToResponse(cartItem, product);
    }

    /**
     * 장바구니 항목 삭제
     */
    public void removeFromCart(Long userId, Long cartItemId) {
        // 본인의 장바구니 항목인지 확인
        boolean exists = cartItemMapper.findByUserId(userId).stream()
                .anyMatch(item -> item.getId().equals(cartItemId));

        if (!exists) {
            throw new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다");
        }

        cartItemMapper.deleteCartItem(cartItemId);
        log.info("장바구니 삭제: cartItemId={}", cartItemId);
    }

    /**
     * 장바구니 비우기
     */
    public void clearCart(Long userId) {
        cartItemMapper.deleteAllByUserId(userId);
        log.info("장바구니 전체 삭제: userId={}", userId);
    }

    /**
     * 장바구니 아이템 개수 조회
     */
    @Transactional(readOnly = true)
    public int getCartCount(Long userId) {
        return cartItemMapper.countByUserId(userId);
    }

    /**
     * CartItem을 CartItemResponse로 변환
     */
    private CartItemResponse convertToResponse(CartItem cartItem, Product product) {
        return CartItemResponse.builder()
                .cartItemId(cartItem.getId())
                .productId(product.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .productImage(product.getImageUrl())
                .quantity(cartItem.getQuantity())
                .subtotal(product.getPrice() * cartItem.getQuantity())
                .build();
    }
}

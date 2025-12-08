package com.example.shoppingjpa.controller;

import com.example.shoppingjpa.config.CustomUserDetails;
import com.example.shoppingjpa.dto.OrderRequest;
import com.example.shoppingjpa.dto.OrderResponse;
import com.example.shoppingjpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 페이지
     */
    @GetMapping("/order")
    public String orderPage() {
        return "order";
    }

    /**
     * 주문 완료 페이지
     */
    @GetMapping("/order-complete")
    public String orderCompletePage() {
        return "order-complete";
    }

    /**
     * 주문 내역 페이지
     */
    @GetMapping("/orders")
    public String ordersPage() {
        return "orders";
    }

    /**
     * 주문 상세 페이지
     */
    @GetMapping("/orders/{id}")
    public String orderDetailPage(@PathVariable Long id) {
        return "order-detail";
    }

    /**
     * 주문 생성 API
     */
    @PostMapping("/api/orders")
    @ResponseBody
    public ResponseEntity<?> createOrder(
            @RequestBody OrderRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = getUserId(userDetails);

        try {
            OrderResponse order = orderService.createOrder(userId, request);
            return ResponseEntity.ok(order);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 주문 조회 API
     */
    @GetMapping("/api/orders/{id}")
    @ResponseBody
    public ResponseEntity<?> getOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        getUserId(userDetails); // 로그인 체크

        try {
            OrderResponse order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 주문 번호로 조회 API
     */
    @GetMapping("/api/orders/number/{orderNumber}")
    @ResponseBody
    public ResponseEntity<?> getOrderByNumber(
            @PathVariable String orderNumber,
            @AuthenticationPrincipal UserDetails userDetails) {

        getUserId(userDetails); // 로그인 체크

        try {
            OrderResponse order = orderService.getOrderByOrderNumber(orderNumber);
            return ResponseEntity.ok(order);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 사용자 주문 목록 API
     */
    @GetMapping("/api/orders")
    @ResponseBody
    public ResponseEntity<?> getUserOrders(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);

        List<OrderResponse> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    /**
     * 결제 처리 API (Mock)
     */
    @PostMapping("/api/orders/{id}/payment")
    @ResponseBody
    public ResponseEntity<?> processPayment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        getUserId(userDetails); // 로그인 체크

        try {
            OrderResponse order = orderService.processPayment(id);
            return ResponseEntity.ok(order);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * UserDetails에서 userId 추출
     */
    private Long getUserId(UserDetails userDetails) {
        if (userDetails instanceof CustomUserDetails) {
            return ((CustomUserDetails) userDetails).getUserId();
        }
        throw new IllegalStateException("사용자 ID를 찾을 수 없습니다");
    }
}

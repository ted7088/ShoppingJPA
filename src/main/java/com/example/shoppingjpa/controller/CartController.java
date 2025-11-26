package com.example.shoppingjpa.controller;

import com.example.shoppingjpa.config.CustomUserDetails;
import com.example.shoppingjpa.dto.CartItemRequest;
import com.example.shoppingjpa.dto.CartItemResponse;
import com.example.shoppingjpa.dto.CartSummaryResponse;
import com.example.shoppingjpa.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 추가
     */
    @PostMapping
    public ResponseEntity<CartItemResponse> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CartItemRequest request) {

        Long userId = getUserId(userDetails);
        CartItemResponse response = cartService.addToCart(userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 장바구니 조회
     */
    @GetMapping
    public ResponseEntity<CartSummaryResponse> getCart(
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = getUserId(userDetails);
        CartSummaryResponse response = cartService.getCartItems(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 수량 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponse> updateQuantity(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestParam Integer quantity) {

        Long userId = getUserId(userDetails);
        CartItemResponse response = cartService.updateQuantity(userId, id, quantity);
        return ResponseEntity.ok(response);
    }

    /**
     * 장바구니 항목 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {

        Long userId = getUserId(userDetails);
        cartService.removeFromCart(userId, id);
        return ResponseEntity.ok().build();
    }

    /**
     * 장바구니 전체 삭제
     */
    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = getUserId(userDetails);
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 장바구니 아이템 개수 조회
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> getCartCount(
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = getUserId(userDetails);
        int count = cartService.getCartCount(userId);
        return ResponseEntity.ok(count);
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

package com.example.shoppingjpa.controller;

import com.example.shoppingjpa.dto.WishlistDTO;
import com.example.shoppingjpa.dto.WishlistRequest;
import com.example.shoppingjpa.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    /**
     * 위시리스트에 상품 추가
     */
    @PostMapping
    public ResponseEntity<?> addToWishlist(@RequestBody WishlistRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
        }

        try {
            wishlistService.addToWishlist(userId, request.getProductId());
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("위시리스트 추가에 실패했습니다");
        }
    }

    /**
     * 사용자의 위시리스트 조회
     */
    @GetMapping
    public ResponseEntity<?> getWishlist(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
        }

        try {
            List<WishlistDTO> wishlist = wishlistService.getWishlist(userId);
            return ResponseEntity.ok(wishlist);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("위시리스트 조회에 실패했습니다");
        }
    }

    /**
     * 위시리스트에서 상품 제거
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Long productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
        }

        try {
            wishlistService.removeFromWishlist(userId, productId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("위시리스트 제거에 실패했습니다");
        }
    }

    /**
     * 상품이 위시리스트에 있는지 확인
     */
    @GetMapping("/check/{productId}")
    public ResponseEntity<?> checkWishlist(@PathVariable Long productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.ok(false);
        }

        try {
            boolean isInWishlist = wishlistService.isInWishlist(userId, productId);
            return ResponseEntity.ok(isInWishlist);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("확인에 실패했습니다");
        }
    }

    /**
     * 위시리스트 개수 조회
     */
    @GetMapping("/count")
    public ResponseEntity<?> getWishlistCount(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.ok(0);
        }

        try {
            int count = wishlistService.getWishlistCount(userId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("개수 조회에 실패했습니다");
        }
    }
}

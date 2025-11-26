package com.example.shoppingjpa.controller;

import com.example.shoppingjpa.dto.PageResponse;
import com.example.shoppingjpa.dto.ProductRequest;
import com.example.shoppingjpa.dto.ProductResponse;
import com.example.shoppingjpa.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 등록 (관리자만)
     */
    @PostMapping
    public ResponseEntity<ProductResponse> registerProduct(
            @Valid @ModelAttribute ProductRequest request,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {

        ProductResponse response = productService.registerProduct(request, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 상품 목록 조회 (페이징, 필터링, 검색)
     */
    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> getProductList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword) {

        PageResponse<ProductResponse> response;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 검색
            response = productService.searchProducts(keyword.trim(), page, size);
        } else if (category != null && !category.trim().isEmpty()) {
            // 카테고리 필터
            response = productService.getProductsByCategory(category, page, size);
        } else {
            // 전체 목록
            response = productService.getProductList(page, size);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 상품 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductResponse response = productService.getProduct(id);
        return ResponseEntity.ok(response);
    }
}

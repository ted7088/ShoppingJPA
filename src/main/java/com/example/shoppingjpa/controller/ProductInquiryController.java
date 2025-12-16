package com.example.shoppingjpa.controller;

import com.example.shoppingjpa.dto.AnswerRequest;
import com.example.shoppingjpa.dto.ProductInquiryRequest;
import com.example.shoppingjpa.dto.ProductInquiryResponse;
import com.example.shoppingjpa.service.ProductInquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductInquiryController {

    private final ProductInquiryService inquiryService;

    /**
     * 문의 등록
     */
    @PostMapping("/api/products/{productId}/inquiries")
    public ResponseEntity<ProductInquiryResponse> createInquiry(
            @PathVariable Long productId,
            @Valid @RequestBody ProductInquiryRequest request) {

        ProductInquiryResponse response = inquiryService.createInquiry(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 상품별 문의 목록 조회
     */
    @GetMapping("/api/products/{productId}/inquiries")
    public ResponseEntity<List<ProductInquiryResponse>> getInquiriesByProduct(
            @PathVariable Long productId) {

        List<ProductInquiryResponse> responses = inquiryService.getInquiriesByProduct(productId);
        return ResponseEntity.ok(responses);
    }

    /**
     * 문의 상세 조회
     */
    @GetMapping("/api/inquiries/{id}")
    public ResponseEntity<ProductInquiryResponse> getInquiry(@PathVariable Long id) {
        ProductInquiryResponse response = inquiryService.getInquiry(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 관리자 답변 등록
     */
    @PostMapping("/api/inquiries/{id}/answer")
    public ResponseEntity<Void> addAnswer(
            @PathVariable Long id,
            @Valid @RequestBody AnswerRequest request) {

        inquiryService.addAnswer(id, request);
        return ResponseEntity.ok().build();
    }

    /**
     * 문의 삭제
     */
    @DeleteMapping("/api/inquiries/{id}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 현재 사용자의 문의 목록 조회
     */
    @GetMapping("/api/users/me/inquiries")
    public ResponseEntity<List<ProductInquiryResponse>> getMyInquiries() {
        List<ProductInquiryResponse> responses = inquiryService.getMyInquiries();
        return ResponseEntity.ok(responses);
    }
}

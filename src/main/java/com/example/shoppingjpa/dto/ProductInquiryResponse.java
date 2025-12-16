package com.example.shoppingjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInquiryResponse {
    private Long id;
    private Long productId;
    private Long userId;
    private String username;
    private String name;
    private String title;
    private String content;
    private Boolean isSecret;
    private String answer;
    private LocalDateTime answeredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 비밀글 여부 표시용
    private Boolean canView; // 현재 사용자가 내용을 볼 수 있는지
}

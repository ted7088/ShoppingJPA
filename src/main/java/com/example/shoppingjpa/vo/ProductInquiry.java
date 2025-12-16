package com.example.shoppingjpa.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInquiry {
    private Long id;
    private Long productId;
    private Long userId;
    private String title;
    private String content;
    private Boolean isSecret;
    private String answer;
    private LocalDateTime answeredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 조인용 필드 (사용자 정보)
    private String username;
    private String name;
}

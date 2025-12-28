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
public class WishlistDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Integer productPrice;
    private String productImageUrl;
    private Integer productStock;
    private String productCategory;
    private LocalDateTime createdAt;
}

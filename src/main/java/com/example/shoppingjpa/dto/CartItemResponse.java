package com.example.shoppingjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private Integer productPrice;
    private String productImage;
    private Integer quantity;
    private Integer subtotal; // 가격 × 수량
}

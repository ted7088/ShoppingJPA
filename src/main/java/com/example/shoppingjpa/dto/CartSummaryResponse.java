package com.example.shoppingjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartSummaryResponse {
    private List<CartItemResponse> items;
    private Integer totalQuantity; // 총 상품 개수
    private Integer totalPrice; // 총 금액
}

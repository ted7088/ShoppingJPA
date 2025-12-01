package com.example.shoppingjpa.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName; // 주문 당시 상품명 스냅샷
    private Integer productPrice; // 주문 당시 가격
    private Integer quantity;
    private Integer subtotal; // productPrice * quantity
}

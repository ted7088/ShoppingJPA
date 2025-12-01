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
public class Order {
    private Long id;
    private Long userId;
    private String orderNumber; // UUID 기반 주문 번호
    private Integer totalAmount;
    private String status; // PENDING, PAID, CANCELLED
    private String paymentMethod; // CARD, BANK_TRANSFER, MOCK
    private LocalDateTime createdAt;
}

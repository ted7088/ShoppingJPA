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
public class Wishlist {
    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime createdAt;
}

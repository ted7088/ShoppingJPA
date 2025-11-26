package com.example.shoppingjpa.mapper;

import com.example.shoppingjpa.vo.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartItemMapper {

    /**
     * 장바구니 추가
     */
    void insertCartItem(CartItem cartItem);

    /**
     * 사용자의 장바구니 목록 조회
     */
    List<CartItem> findByUserId(@Param("userId") Long userId);

    /**
     * 특정 사용자의 특정 상품 조회
     */
    Optional<CartItem> findByUserIdAndProductId(@Param("userId") Long userId,
            @Param("productId") Long productId);

    /**
     * 수량 변경
     */
    void updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    /**
     * 장바구니 항목 삭제
     */
    void deleteCartItem(@Param("id") Long id);

    /**
     * 사용자의 장바구니 전체 삭제
     */
    void deleteAllByUserId(@Param("userId") Long userId);

    /**
     * 사용자의 장바구니 아이템 개수
     */
    int countByUserId(@Param("userId") Long userId);
}

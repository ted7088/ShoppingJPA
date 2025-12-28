package com.example.shoppingjpa.mapper;

import com.example.shoppingjpa.vo.Wishlist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface WishlistMapper {

    /**
     * 위시리스트 추가
     */
    void insertWishlist(Wishlist wishlist);

    /**
     * 사용자의 위시리스트 목록 조회
     */
    List<Wishlist> findByUserId(@Param("userId") Long userId);

    /**
     * 특정 사용자의 특정 상품 조회
     */
    Optional<Wishlist> findByUserIdAndProductId(@Param("userId") Long userId,
            @Param("productId") Long productId);

    /**
     * 위시리스트 항목 삭제
     */
    void deleteWishlist(@Param("id") Long id);

    /**
     * 사용자와 상품으로 위시리스트 삭제
     */
    void deleteByUserIdAndProductId(@Param("userId") Long userId,
            @Param("productId") Long productId);

    /**
     * 사용자의 위시리스트 개수
     */
    int countByUserId(@Param("userId") Long userId);
}

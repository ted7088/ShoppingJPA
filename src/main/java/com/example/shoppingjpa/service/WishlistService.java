package com.example.shoppingjpa.service;

import com.example.shoppingjpa.dto.WishlistDTO;
import com.example.shoppingjpa.mapper.ProductMapper;
import com.example.shoppingjpa.mapper.WishlistMapper;
import com.example.shoppingjpa.vo.Product;
import com.example.shoppingjpa.vo.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistMapper wishlistMapper;
    private final ProductMapper productMapper;

    /**
     * 위시리스트에 상품 추가
     */
    @Transactional
    public void addToWishlist(Long userId, Long productId) {
        // 이미 위시리스트에 있는지 확인
        Optional<Wishlist> existing = wishlistMapper.findByUserIdAndProductId(userId, productId);
        if (existing.isPresent()) {
            throw new IllegalStateException("이미 위시리스트에 추가된 상품입니다");
        }

        // 상품이 존재하는지 확인
        Optional<Product> product = productMapper.findById(productId);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다");
        }

        // 위시리스트에 추가
        Wishlist wishlist = Wishlist.builder()
                .userId(userId)
                .productId(productId)
                .build();
        wishlistMapper.insertWishlist(wishlist);
    }

    /**
     * 사용자의 위시리스트 조회 (상품 정보 포함)
     */
    @Transactional(readOnly = true)
    public List<WishlistDTO> getWishlist(Long userId) {
        List<Wishlist> wishlistItems = wishlistMapper.findByUserId(userId);
        List<WishlistDTO> result = new ArrayList<>();

        for (Wishlist item : wishlistItems) {
            Optional<Product> productOpt = productMapper.findById(item.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                WishlistDTO dto = WishlistDTO.builder()
                        .id(item.getId())
                        .productId(product.getId())
                        .productName(product.getName())
                        .productPrice(product.getPrice())
                        .productImageUrl(product.getImageUrl())
                        .productStock(product.getStock())
                        .productCategory(product.getCategory())
                        .createdAt(item.getCreatedAt())
                        .build();
                result.add(dto);
            }
        }

        return result;
    }

    /**
     * 위시리스트에서 상품 제거
     */
    @Transactional
    public void removeFromWishlist(Long userId, Long productId) {
        wishlistMapper.deleteByUserIdAndProductId(userId, productId);
    }

    /**
     * 상품이 위시리스트에 있는지 확인
     */
    @Transactional(readOnly = true)
    public boolean isInWishlist(Long userId, Long productId) {
        Optional<Wishlist> wishlist = wishlistMapper.findByUserIdAndProductId(userId, productId);
        return wishlist.isPresent();
    }

    /**
     * 위시리스트 개수 조회
     */
    @Transactional(readOnly = true)
    public int getWishlistCount(Long userId) {
        return wishlistMapper.countByUserId(userId);
    }
}

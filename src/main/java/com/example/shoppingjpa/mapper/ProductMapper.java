package com.example.shoppingjpa.mapper;

import com.example.shoppingjpa.vo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {

    /**
     * 상품 등록
     */
    void insertProduct(Product product);

    /**
     * ID로 상품 조회
     */
    Optional<Product> findById(@Param("id") Long id);

    /**
     * 전체 상품 목록 조회 (페이징)
     */
    List<Product> findAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 카테고리별 상품 조회 (페이징)
     */
    List<Product> findByCategory(@Param("category") String category,
            @Param("offset") int offset,
            @Param("limit") int limit);

    /**
     * 상품명 검색 (페이징)
     */
    List<Product> searchByName(@Param("keyword") String keyword,
            @Param("offset") int offset,
            @Param("limit") int limit);

    /**
     * 전체 상품 수
     */
    int countAll();

    /**
     * 카테고리별 상품 수
     */
    int countByCategory(@Param("category") String category);

    /**
     * 검색 결과 수
     */
    int countByKeyword(@Param("keyword") String keyword);

    /**
     * 상품 수정
     */
    void updateProduct(Product product);

    /**
     * 상품 삭제
     */
    void deleteProduct(@Param("id") Long id);
}

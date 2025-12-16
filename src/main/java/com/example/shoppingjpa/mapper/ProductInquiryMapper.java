package com.example.shoppingjpa.mapper;

import com.example.shoppingjpa.vo.ProductInquiry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductInquiryMapper {

    /**
     * 문의 등록
     */
    void insertInquiry(ProductInquiry inquiry);

    /**
     * 상품별 문의 목록 조회
     */
    List<ProductInquiry> findByProductId(@Param("productId") Long productId);

    /**
     * 문의 상세 조회
     */
    ProductInquiry findById(@Param("id") Long id);

    /**
     * 관리자 답변 등록
     */
    void updateAnswer(@Param("id") Long id, @Param("answer") String answer);

    /**
     * 문의 삭제
     */
    void deleteInquiry(@Param("id") Long id);

    /**
     * 상품별 문의 개수
     */
    int countByProductId(@Param("productId") Long productId);

    /**
     * 사용자별 문의 목록 조회
     */
    List<ProductInquiry> findByUserId(@Param("userId") Long userId);
}

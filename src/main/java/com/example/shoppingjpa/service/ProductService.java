package com.example.shoppingjpa.service;

import com.example.shoppingjpa.dto.PageResponse;
import com.example.shoppingjpa.dto.ProductRequest;
import com.example.shoppingjpa.dto.ProductResponse;
import com.example.shoppingjpa.mapper.ProductMapper;
import com.example.shoppingjpa.vo.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductMapper productMapper;
    private final FileUploadService fileUploadService;

    /**
     * 상품 등록
     */
    public ProductResponse registerProduct(ProductRequest request, MultipartFile imageFile) throws IOException {
        // 이미지 파일 업로드
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = fileUploadService.uploadFile(imageFile);
        }

        // Product 객체 생성
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(request.getCategory())
                .imageUrl(imageUrl)
                .build();

        // DB에 저장
        productMapper.insertProduct(product);

        log.info("상품 등록 완료: {}", product.getId());

        // ProductResponse로 변환하여 반환
        return convertToProductResponse(product);
    }

    /**
     * 상품 조회
     */
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product product = productMapper.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));

        return convertToProductResponse(product);
    }

    /**
     * 전체 상품 목록 조회 (페이징)
     */
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getProductList(int page, int size) {
        int offset = page * size;
        List<Product> products = productMapper.findAll(offset, size);
        int totalElements = productMapper.countAll();

        return buildPageResponse(products, page, size, totalElements);
    }

    /**
     * 카테고리별 상품 조회 (페이징)
     */
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getProductsByCategory(String category, int page, int size) {
        int offset = page * size;
        List<Product> products = productMapper.findByCategory(category, offset, size);
        int totalElements = productMapper.countByCategory(category);

        return buildPageResponse(products, page, size, totalElements);
    }

    /**
     * 상품 검색 (페이징)
     */
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> searchProducts(String keyword, int page, int size) {
        int offset = page * size;
        List<Product> products = productMapper.searchByName(keyword, offset, size);
        int totalElements = productMapper.countByKeyword(keyword);

        return buildPageResponse(products, page, size, totalElements);
    }

    /**
     * PageResponse 생성 헬퍼 메서드
     */
    private PageResponse<ProductResponse> buildPageResponse(List<Product> products, int page, int size,
            int totalElements) {
        List<ProductResponse> content = products.stream()
                .map(this::convertToProductResponse)
                .toList();

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<ProductResponse>builder()
                .content(content)
                .currentPage(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .first(page == 0)
                .last(page >= totalPages - 1)
                .build();
    }

    /**
     * Product를 ProductResponse로 변환
     */
    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}

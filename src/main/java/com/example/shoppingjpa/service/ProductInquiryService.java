package com.example.shoppingjpa.service;

import com.example.shoppingjpa.dto.AnswerRequest;
import com.example.shoppingjpa.dto.ProductInquiryRequest;
import com.example.shoppingjpa.dto.ProductInquiryResponse;
import com.example.shoppingjpa.mapper.ProductInquiryMapper;
import com.example.shoppingjpa.vo.ProductInquiry;
import com.example.shoppingjpa.vo.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductInquiryService {

    private final ProductInquiryMapper inquiryMapper;
    private final HttpSession session;

    /**
     * 문의 등록
     */
    public ProductInquiryResponse createInquiry(Long productId, ProductInquiryRequest request) {
        User currentUser = getCurrentUser();

        ProductInquiry inquiry = ProductInquiry.builder()
                .productId(productId)
                .userId(currentUser.getId())
                .title(request.getTitle())
                .content(request.getContent())
                .isSecret(request.getIsSecret())
                .build();

        inquiryMapper.insertInquiry(inquiry);
        log.info("문의 등록 완료: productId={}, inquiryId={}", productId, inquiry.getId());

        // 등록된 문의 조회 (사용자 정보 포함)
        ProductInquiry savedInquiry = inquiryMapper.findById(inquiry.getId());
        return convertToResponse(savedInquiry, currentUser);
    }

    /**
     * 상품별 문의 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ProductInquiryResponse> getInquiriesByProduct(Long productId) {
        User currentUser = getCurrentUserOrNull();
        List<ProductInquiry> inquiries = inquiryMapper.findByProductId(productId);

        return inquiries.stream()
                .map(inquiry -> convertToResponse(inquiry, currentUser))
                .collect(Collectors.toList());
    }

    /**
     * 문의 상세 조회
     */
    @Transactional(readOnly = true)
    public ProductInquiryResponse getInquiry(Long id) {
        User currentUser = getCurrentUserOrNull();
        ProductInquiry inquiry = inquiryMapper.findById(id);

        if (inquiry == null) {
            throw new IllegalArgumentException("문의를 찾을 수 없습니다");
        }

        // 비밀글 권한 체크
        if (inquiry.getIsSecret() && !canViewSecretInquiry(inquiry, currentUser)) {
            throw new IllegalArgumentException("비밀글은 작성자와 관리자만 조회할 수 있습니다");
        }

        return convertToResponse(inquiry, currentUser);
    }

    /**
     * 관리자 답변 등록
     */
    public void addAnswer(Long id, AnswerRequest request) {
        User currentUser = getCurrentUser();

        // 관리자 권한 체크
        if (!"ADMIN".equals(currentUser.getRole())) {
            throw new IllegalArgumentException("관리자만 답변을 등록할 수 있습니다");
        }

        ProductInquiry inquiry = inquiryMapper.findById(id);
        if (inquiry == null) {
            throw new IllegalArgumentException("문의를 찾을 수 없습니다");
        }

        inquiryMapper.updateAnswer(id, request.getAnswer());
        log.info("답변 등록 완료: inquiryId={}", id);
    }

    /**
     * 문의 삭제
     */
    public void deleteInquiry(Long id) {
        User currentUser = getCurrentUser();
        ProductInquiry inquiry = inquiryMapper.findById(id);

        if (inquiry == null) {
            throw new IllegalArgumentException("문의를 찾을 수 없습니다");
        }

        // 작성자 본인 또는 관리자만 삭제 가능
        if (!inquiry.getUserId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRole())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다");
        }

        inquiryMapper.deleteInquiry(id);
        log.info("문의 삭제 완료: inquiryId={}", id);
    }

    /**
     * 현재 사용자의 문의 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ProductInquiryResponse> getMyInquiries() {
        User currentUser = getCurrentUser();
        List<ProductInquiry> inquiries = inquiryMapper.findByUserId(currentUser.getId());

        return inquiries.stream()
                .map(inquiry -> convertToResponse(inquiry, currentUser))
                .collect(Collectors.toList());
    }

    /**
     * ProductInquiry를 ProductInquiryResponse로 변환
     */
    private ProductInquiryResponse convertToResponse(ProductInquiry inquiry, User currentUser) {
        boolean canView = canViewSecretInquiry(inquiry, currentUser);

        ProductInquiryResponse response = ProductInquiryResponse.builder()
                .id(inquiry.getId())
                .productId(inquiry.getProductId())
                .userId(inquiry.getUserId())
                .username(inquiry.getUsername())
                .name(inquiry.getName() == null ? "" : inquiry.getName())
                .title(inquiry.getTitle())
                .isSecret(inquiry.getIsSecret())
                .answeredAt(inquiry.getAnsweredAt())
                .createdAt(inquiry.getCreatedAt())
                .updatedAt(inquiry.getUpdatedAt())
                .canView(canView)
                .build();

        // 비밀글이고 권한이 없으면 내용과 답변 숨김
        if (inquiry.getIsSecret() && !canView) {
            response.setContent("비밀글입니다");
            response.setAnswer(null);
        } else {
            response.setContent(inquiry.getContent());
            response.setAnswer(inquiry.getAnswer());
        }

        return response;
    }

    /**
     * 비밀글 조회 권한 체크
     */
    private boolean canViewSecretInquiry(ProductInquiry inquiry, User currentUser) {
        if (!inquiry.getIsSecret()) {
            return true;
        }

        if (currentUser == null) {
            return false;
        }

        // 작성자 본인 또는 관리자
        return inquiry.getUserId().equals(currentUser.getId()) || "ADMIN".equals(currentUser.getRole());
    }

    /**
     * 현재 로그인한 사용자 조회
     */
    private User getCurrentUser() {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        return user;
    }

    /**
     * 현재 로그인한 사용자 조회 (없으면 null 반환)
     */
    private User getCurrentUserOrNull() {
        return (User) session.getAttribute("user");
    }
}

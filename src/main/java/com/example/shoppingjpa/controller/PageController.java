package com.example.shoppingjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    /**
     * 메인 페이지 - 상품 목록으로 리다이렉트
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 상품 등록 페이지 (관리자)
     */
    @GetMapping("/admin/products/register")
    public String productRegister() {
        return "admin/product-register";
    }

    /**
     * 상품 목록 페이지
     */
    @GetMapping("/products")
    public String productList() {
        return "products";
    }

    /**
     * 상품 상세 페이지
     */
    @GetMapping("/products/{id}")
    public String productDetail() {
        return "product-detail";
    }

    /**
     * 장바구니 페이지
     */
    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}

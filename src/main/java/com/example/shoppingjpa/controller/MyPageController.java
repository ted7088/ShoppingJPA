package com.example.shoppingjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    /**
     * 마이페이지
     */
    @GetMapping("/mypage")
    public String myPage() {
        return "mypage";
    }
}

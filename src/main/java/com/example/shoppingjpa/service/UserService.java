package com.example.shoppingjpa.service;

import com.example.shoppingjpa.dto.LoginRequest;
import com.example.shoppingjpa.dto.SignupRequest;
import com.example.shoppingjpa.dto.UserResponse;
import com.example.shoppingjpa.exception.DuplicateUserException;
import com.example.shoppingjpa.mapper.UserMapper;
import com.example.shoppingjpa.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * 회원가입
     */
    public UserResponse signup(SignupRequest request) {
        // 중복 체크
        if (userMapper.existsByUsername(request.getUsername()) > 0) {
            throw new DuplicateUserException("이미 사용 중인 아이디입니다");
        }

        if (userMapper.existsByEmail(request.getEmail()) > 0) {
            throw new DuplicateUserException("이미 사용 중인 이메일입니다");
        }

        // User 객체 생성 및 비밀번호 암호화
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .role("USER")
                .build();

        // DB에 저장
        userMapper.insertUser(user);

        // UserResponse로 변환하여 반환
        return convertToUserResponse(user);
    }

    /**
     * 로그인
     */
    public UserResponse login(LoginRequest request) {
        // Spring Security를 통한 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        // SecurityContext에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 사용자 정보 조회 및 반환
        User user = userMapper.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        return convertToUserResponse(user);
    }

    /**
     * 현재 로그인한 사용자 정보 조회
     */
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        String username = authentication.getName();
        User user = userMapper.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        return convertToUserResponse(user);
    }

    /**
     * 아이디 중복 체크
     */
    @Transactional(readOnly = true)
    public boolean checkUsernameDuplicate(String username) {
        return userMapper.existsByUsername(username) > 0;
    }

    /**
     * 이메일 중복 체크
     */
    @Transactional(readOnly = true)
    public boolean checkEmailDuplicate(String email) {
        return userMapper.existsByEmail(email) > 0;
    }

    /**
     * User를 UserResponse로 변환
     */
    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

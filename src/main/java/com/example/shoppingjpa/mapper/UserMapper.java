package com.example.shoppingjpa.mapper;

import com.example.shoppingjpa.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {

    /**
     * 회원 등록
     */
    void insertUser(User user);

    /**
     * 사용자명으로 사용자 조회
     */
    Optional<User> findByUsername(@Param("username") String username);

    /**
     * 이메일로 사용자 조회
     */
    Optional<User> findByEmail(@Param("email") String email);

    /**
     * 사용자명 중복 체크
     */
    int existsByUsername(@Param("username") String username);

    /**
     * 이메일 중복 체크
     */
    int existsByEmail(@Param("email") String email);

    /**
     * ID로 사용자 조회
     */
    Optional<User> findById(@Param("id") Long id);
}

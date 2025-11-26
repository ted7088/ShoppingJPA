-- 쇼핑몰 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS shopping_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shopping_db;

-- 사용자 테이블 생성
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE COMMENT '로그인 아이디',
    password VARCHAR(255) NOT NULL COMMENT '암호화된 비밀번호',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '이메일',
    name VARCHAR(20) NOT NULL COMMENT '이름',
    phone VARCHAR(20) NOT NULL COMMENT '전화번호',
    address VARCHAR(255) NOT NULL COMMENT '주소',
    role VARCHAR(10) NOT NULL DEFAULT 'USER' COMMENT '권한 (USER, ADMIN)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='사용자 정보';

-- 관리자 계정 생성 (비밀번호: admin1234)
-- BCrypt 암호화된 비밀번호
-- 이미 존재하면 무시
INSERT IGNORE INTO users (username, password, email, name, phone, address, role)
VALUES ('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 
        'admin@shopping.com', '관리자', '010-0000-0000', '서울시 강남구', 'ADMIN');

-- 상품 테이블 생성
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '상품명',
    description TEXT COMMENT '상품 설명',
    price INT NOT NULL COMMENT '가격',
    stock INT NOT NULL DEFAULT 0 COMMENT '재고 수량',
    category VARCHAR(50) COMMENT '카테고리',
    image_url VARCHAR(255) COMMENT '이미지 경로',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    INDEX idx_category (category),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='상품 정보';

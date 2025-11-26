-- 샘플 상품 데이터 (의류 및 도서)
USE shopping_db;

-- 의류 카테고리 상품
INSERT INTO products (name, description, price, stock, category, image_url, created_at, updated_at) VALUES
('클래식 화이트 셔츠', '깔끔한 디자인의 남성용 화이트 셔츠입니다. 면 100% 소재로 편안한 착용감을 제공합니다.', 45000, 50, '의류', 'https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=500', NOW(), NOW()),
('데님 청바지', '클래식한 스타일의 남성용 청바지입니다. 내구성이 뛰어나며 다양한 스타일에 매치 가능합니다.', 65000, 30, '의류', 'https://images.unsplash.com/photo-1542272604-787c3835535d?w=500', NOW(), NOW()),
('캐주얼 후드티', '부드러운 기모 안감의 후드티입니다. 가을/겨울 시즌에 적합합니다.', 38000, 40, '의류', 'https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=500', NOW(), NOW()),
('여성 니트 스웨터', '따뜻하고 부드러운 니트 스웨터입니다. 베이직한 디자인으로 어디에나 잘 어울립니다.', 52000, 25, '의류', 'https://images.unsplash.com/photo-1576566588028-4147f3842f27?w=500', NOW(), NOW()),
('레더 재킷', '고급스러운 인조 가죽 재킷입니다. 세련된 디자인으로 멋스러운 스타일을 연출할 수 있습니다.', 120000, 15, '의류', 'https://images.unsplash.com/photo-1551028719-00167b16eac5?w=500', NOW(), NOW()),
('면 티셔츠 (3종 세트)', '베이직한 라운드넥 티셔츠 3장 세트입니다. 화이트, 블랙, 그레이 색상 포함.', 29000, 60, '의류', 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500', NOW(), NOW()),
('슬림핏 정장 바지', '비즈니스 캐주얼에 적합한 슬림핏 정장 바지입니다. 신축성 있는 소재로 편안합니다.', 58000, 20, '의류', 'https://images.unsplash.com/photo-1473966968600-fa801b869a1a?w=500', NOW(), NOW()),
('롱 코트', '우아한 디자인의 여성용 롱 코트입니다. 겨울철 필수 아이템입니다.', 150000, 10, '의류', 'https://images.unsplash.com/photo-1539533113208-f6df8cc8b543?w=500', NOW(), NOW()),
('스포츠 트레이닝복', '운동하기 편한 스포츠 트레이닝복 세트입니다. 통기성이 우수합니다.', 68000, 35, '의류', 'https://images.unsplash.com/photo-1556906781-9a412961c28c?w=500', NOW(), NOW()),
('원피스', '여름용 플로럴 패턴 원피스입니다. 시원하고 가벼운 소재로 제작되었습니다.', 42000, 28, '의류', 'https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=500', NOW(), NOW());

-- 도서 카테고리 상품
INSERT INTO products (name, description, price, stock, category, image_url, created_at, updated_at) VALUES
('클린 코드', '로버트 C. 마틴 저. 애자일 소프트웨어 장인 정신. 깨끗한 코드를 작성하는 방법을 배울 수 있습니다.', 33000, 45, '도서', 'https://images.unsplash.com/photo-1532012197267-da84d127e765?w=500', NOW(), NOW()),
('이펙티브 자바', '조슈아 블로크 저. 자바 프로그래밍 언어를 효과적으로 사용하는 90가지 방법을 소개합니다.', 36000, 38, '도서', 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=500', NOW(), NOW()),
('리팩터링 2판', '마틴 파울러 저. 코드 구조를 체계적으로 개선하여 효율적인 리팩터링 기법을 배웁니다.', 38000, 30, '도서', 'https://images.unsplash.com/photo-1512820790803-83ca734da794?w=500', NOW(), NOW()),
('스프링 부트 실전 활용 마스터', '스프링 부트를 활용한 실무 프로젝트 개발 가이드. 초보자부터 중급자까지 추천합니다.', 32000, 42, '도서', 'https://images.unsplash.com/photo-1589998059171-988d887df646?w=500', NOW(), NOW()),
('객체지향의 사실과 오해', '조영호 저. 역할, 책임, 협력 관점에서 본 객체지향 프로그래밍의 본질을 다룹니다.', 25000, 50, '도서', 'https://images.unsplash.com/photo-1497633762265-9d179a990aa6?w=500', NOW(), NOW()),
('모던 자바 인 액션', '자바 8, 9, 10, 11의 새로운 기능을 실무에 적용하는 방법을 소개합니다.', 39000, 25, '도서', 'https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=500', NOW(), NOW()),
('데이터베이스 첫걸음', 'SQL과 데이터베이스 설계의 기초를 쉽게 배울 수 있는 입문서입니다.', 18000, 55, '도서', 'https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?w=500', NOW(), NOW()),
('HTTP 완벽 가이드', '웹의 핵심 기술인 HTTP 프로토콜을 상세하게 설명하는 바이블입니다.', 45000, 20, '도서', 'https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=500', NOW(), NOW()),
('알고리즘 문제 해결 전략', '프로그래밍 대회 문제를 통해 알고리즘 설계 능력을 키울 수 있습니다.', 42000, 32, '도서', 'https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=500', NOW(), NOW()),
('실용주의 프로그래머', '앤드류 헌트, 데이비드 토머스 저. 프로그래머로서 성장하기 위한 실용적인 조언들을 담았습니다.', 34000, 40, '도서', 'https://images.unsplash.com/photo-1495446815901-a7297e633e8d?w=500', NOW(), NOW());

-- 전자제품 카테고리 상품 (보너스)
INSERT INTO products (name, description, price, stock, category, image_url, created_at, updated_at) VALUES
('무선 이어폰', '노이즈 캔슬링 기능이 탑재된 고음질 무선 이어폰입니다. 최대 24시간 재생 가능합니다.', 89000, 100, '전자제품', 'https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=500', NOW(), NOW()),
('블루투스 스피커', '360도 사운드를 제공하는 포터블 블루투스 스피커입니다. 방수 기능 지원.', 65000, 75, '전자제품', 'https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=500', NOW(), NOW()),
('스마트워치', '건강 관리 및 운동 트래킹 기능이 있는 스마트워치입니다. iOS/Android 호환.', 250000, 50, '전자제품', 'https://images.unsplash.com/photo-1579586337278-3befd40fd17a?w=500', NOW(), NOW()),
('노트북 거치대', '알루미늄 소재의 프리미엄 노트북 거치대입니다. 각도 조절 가능.', 35000, 80, '전자제품', 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=500', NOW(), NOW()),
('USB-C 멀티 허브', '7-in-1 USB-C 멀티포트 허브입니다. HDMI, USB 3.0, SD 카드 리더 포함.', 42000, 90, '전자제품', 'https://images.unsplash.com/photo-1625948515291-69613efd103f?w=500', NOW(), NOW());

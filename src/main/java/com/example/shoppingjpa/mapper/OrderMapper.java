package com.example.shoppingjpa.mapper;

import com.example.shoppingjpa.vo.Order;
import com.example.shoppingjpa.vo.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderMapper {

    /**
     * 주문 생성
     */
    void insertOrder(Order order);

    /**
     * 주문 상품 추가
     */
    void insertOrderItem(OrderItem orderItem);

    /**
     * 주문 조회 (ID)
     */
    Optional<Order> findById(Long id);

    /**
     * 주문 번호로 조회
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    /**
     * 사용자별 주문 목록
     */
    List<Order> findByUserId(Long userId);

    /**
     * 주문 상품 목록 조회
     */
    List<OrderItem> findOrderItemsByOrderId(Long orderId);

    /**
     * 주문 상태 업데이트
     */
    void updateOrderStatus(@Param("id") Long id, @Param("status") String status);
}

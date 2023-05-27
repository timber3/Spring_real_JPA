package com.real_JPA.JPA_SHOP.repository;

import com.real_JPA.JPA_SHOP.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName; // 회원이름
    private OrderStatus orderStatus; // 주문 상태
}

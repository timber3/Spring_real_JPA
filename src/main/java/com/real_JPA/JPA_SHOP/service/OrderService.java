package com.real_JPA.JPA_SHOP.service;

import com.real_JPA.JPA_SHOP.domain.Delivery;
import com.real_JPA.JPA_SHOP.domain.Member;
import com.real_JPA.JPA_SHOP.domain.Order;
import com.real_JPA.JPA_SHOP.domain.OrderItem;
import com.real_JPA.JPA_SHOP.domain.item.Item;
import com.real_JPA.JPA_SHOP.repository.ItemRepository;
import com.real_JPA.JPA_SHOP.repository.MemberRepository;
import com.real_JPA.JPA_SHOP.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.CreateOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }
}

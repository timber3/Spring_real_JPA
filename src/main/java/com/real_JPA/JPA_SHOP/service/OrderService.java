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
        // OrderItem은 static 메서드로 생성하게 되는데
        // 누군가는 new 생성으로 대입하는 사람이 있을것이다.
        // 어디서는 static메서드로, 어디서는 new로 생성하게 된다면 유지보수의 관점에서 좋지않다.
        // 그래서 기본 생성자를 protected로 막아준다.
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.CreateOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {

    }
}

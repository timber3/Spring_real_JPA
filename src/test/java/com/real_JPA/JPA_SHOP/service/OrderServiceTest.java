package com.real_JPA.JPA_SHOP.service;

import com.real_JPA.JPA_SHOP.domain.Address;
import com.real_JPA.JPA_SHOP.domain.Member;
import com.real_JPA.JPA_SHOP.domain.Order;
import com.real_JPA.JPA_SHOP.domain.OrderStatus;
import com.real_JPA.JPA_SHOP.domain.item.Book;
import com.real_JPA.JPA_SHOP.domain.item.Item;
import com.real_JPA.JPA_SHOP.exception.NotEnoughStockException;
import com.real_JPA.JPA_SHOP.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() {
        // given
        Member member = createMember();
        Item book = createBook("돈 버는법", 15000, 10);

        int orderCount = 2;
        // when

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 15000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량 만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());

    }



    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() {
        // given
        Member member = createMember();
        Item book = createBook("돈 버는법", 15000, 10);

        int orderCount = 11;
        // when

        orderService.order(member.getId(), book.getId(), orderCount);

        // then
        fail("재고 수량 부족 예외 발생 해야한다.");
    }

    @Test
    public void 주문취소() {
        // given
        Member member = createMember();
        Item book = createBook("JPA", 15000, 10);

        int orderCount = 2;

        // when

        orderService.order(member.getId(), book.getId(), orderCount);

        // then
    }


    private Item createBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("대구", "침산", "1234-1" ));
        em.persist(member);
        return member;
    }

}
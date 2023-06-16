package com.real_JPA.JPA_SHOP.repository;

import com.real_JPA.JPA_SHOP.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        String jpql = "select o from Order o join o.member m";
        List<Order> resultList = em.createQuery(jpql +
                        " where o.status = :status" +
                        " and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000) // 최대 1000건
                .getResultList();

        return resultList;

        // 동적 쿼리 처리하기 (복잡함) 이렇게 쓰지 말고 QueryDSL을 사용하도록 하자

        /*public List<Order> findAllByString(OrderSearch orderSearch) {
            //language=JPAQL
            String jpql = "select o From Order o join o.member m";
            boolean isFirstCondition = true;
            //주문 상태 검색
            if (orderSearch.getOrderStatus() != null) {
                if (isFirstCondition) {
                    jpql += " where";
                    isFirstCondition = false;
                } else {
                    jpql += " and";
                }
                jpql += " o.status = :status";
            }
            //회원 이름 검색
            if (StringUtils.hasText(orderSearch.getMemberName())) {
                if (isFirstCondition) {
                    jpql += " where";
                    isFirstCondition = false;
                } else {
                    jpql += " and";
                }
                jpql += " m.name like :name";
            }
            TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                    .setMaxResults(1000); //최대 1000건
            if (orderSearch.getOrderStatus() != null) {
                query = query.setParameter("status", orderSearch.getOrderStatus());
            }
            if (StringUtils.hasText(orderSearch.getMemberName())) {
                query = query.setParameter("name", orderSearch.getMemberName());
            }
            return query.getResultList();
        }*/

    }
}

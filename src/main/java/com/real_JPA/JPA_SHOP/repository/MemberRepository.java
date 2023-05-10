package com.real_JPA.JPA_SHOP.repository;

import com.real_JPA.JPA_SHOP.domain.Member;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long Id) {
        return em.find(Member.class, Id);
    }
}

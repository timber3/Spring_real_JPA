package com.real_JPA.JPA_SHOP.repository;

import com.real_JPA.JPA_SHOP.domain.Member;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    // EntityManagerFactory에서 만들어 주거나 만들어져있는 em을 넣어준다.
    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    // 단건조회, pk으로 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // JPQL을 통한 모든 멤버 조회
    public List<Member> findAll() {
        // JPQL을 사용해야 한다. (객체를 대상으로 사용하는 쿼리라 이해하면 된다.)
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 이름으로 조회
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}

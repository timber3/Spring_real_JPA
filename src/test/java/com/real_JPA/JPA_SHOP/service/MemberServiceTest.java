package com.real_JPA.JPA_SHOP.service;

import com.real_JPA.JPA_SHOP.domain.Member;
import com.real_JPA.JPA_SHOP.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

/*
Test 에서의 @Transactional은 기본적으로 @Rollback 의 기능을 가지므로 실제 값이 들어가는것을 확인하려면 @Rollback(false)값을 지정해줘야 한다.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() {
        // given
        Member member = new Member();
        member.setName("Oh");

        // when
        Long savedId = memberService.join(member);

        // then
        //em.flush(); // 트랜잭션에 있는 쿼리를 강제로 commit 해버림 , 쿼리를 날림 ( @Transactional에 의해 롤백됨 - 실제 반영 x )
        assertThat(member).isEqualTo(memberRepository.findOne(savedId));
        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("Kim");
        member2.setName("Kim");

        // when
        memberService.join(member1);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> { memberService.join(member2);});
    }

}
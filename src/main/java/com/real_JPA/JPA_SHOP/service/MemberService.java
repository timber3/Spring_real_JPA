package com.real_JPA.JPA_SHOP.service;

import com.real_JPA.JPA_SHOP.domain.Member;
import com.real_JPA.JPA_SHOP.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
서비스에 @Transactional을 붙이는 이유는 우리가 만든 서비스 로직들은 JPA 트랜잭션 처리가 안되어있기 때문이다.
Repository의 persist나 find 등 JPA 메소드들은 트랜잭션 처리가 되어있기 때문에 안해도 된다.
 */

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        // Exception
        if(!memberRepository.findByName(member.getName()).isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}

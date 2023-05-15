package com.real_JPA.JPA_SHOP.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(
        uniqueConstraints = {
                // 유니크 제약조건 걸기
                @UniqueConstraint(columnNames = {"name"})
        }
)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    // 사용자 지정 타입
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}

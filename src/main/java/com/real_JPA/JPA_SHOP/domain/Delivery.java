package com.real_JPA.JPA_SHOP.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // enum 을 사용할 때는 EnumType을 지정해야 하는데
    // EnumType.ORDINAL 은 1, 2, 3이런식으로 지정되는것이고
    // EnumType.STRING 은 string 그 자체로 사용하는 방식인데
    // ORDINAL은 절대 사용하지 말자 ( enum 클래스의 attr 순서가 바뀌면 값이 다 바뀌게 됨 )
    // @Enumerate 디폴트는 ORDINAL임
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;  // READY, COMP

}

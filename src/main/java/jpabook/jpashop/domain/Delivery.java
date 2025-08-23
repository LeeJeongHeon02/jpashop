package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // EnumType은 2가지가 있는데, String으로 써야 나중에 enum클래스를 수정해도 문제가 안생긴다.
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}

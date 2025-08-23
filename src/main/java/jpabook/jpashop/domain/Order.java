package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

//    CascadeType.ALL의 의미
//    CascadeType.ALL은 모든 영속성 작업(CREATE, UPDATE, DELETE 등)에 대해
//    영속성 전이를 적용하겠다는 의미입니다.
//    즉, 부모 객체인 **Order(주문)**에 대해 어떤 작업을 수행하면
//    연관된 자식 객체인 **OrderItem(주문 상품)**에도 동일한 작업이 자동으로 적용됩니다.
//
//    이것이 중요한 이유는, 만약 cascade 설정을 하지 않았다면 Order 객체를 저장할 때
//    OrderItem 객체들을 각각 별도로 저장해야 하기 때문입니다.
//    OrderItem 객체들이 한두 개라면 문제가 없겠지만, 수십 수백 개라면 코드가 매우 번거로워지겠죠.
//
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>(); // 주문한 상품들

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // 배송 정보

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태를 나타내는 Enum타입

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}

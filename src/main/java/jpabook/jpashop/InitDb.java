package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
        System.out.println("Finish initDb");
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("member1", "서울", "월정로", "35367");
            em.persist(member);

            Book book1 = createBook("Backend book1", 18000, 200);
            em.persist(book1);

            Book book2 = createBook("Backend book2", 25000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 18000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 25000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("member2", "대전", "도안동로", "07766");
            em.persist(member);

            Book book1 = createBook("Frontend book1", 12000, 200);
            em.persist(book1);

            Book book2 = createBook("Frontend book2", 21000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 12000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 21000, 4);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private static Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private static Member createMember(String userName, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(userName);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }


}

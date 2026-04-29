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
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Transactional
    @Component
    @RequiredArgsConstructor
    private static class InitService {

        private final EntityManager em;

        public void dbInit1(){
            Member member = createMember("UserA", "서울", "1", "111");
            em.persist(member);

            Book book1 = createBook("spring1", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("spring2", 30000, 300);
            em.persist(book2);

            Delivery delivery = createDelivery(member);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 10);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 20);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2(){
            Member member = createMember("UserB", "전주", "2", "222");
            em.persist(member);

            Book book1 = createBook("jpa1", 40000, 400);
            em.persist(book1);

            Book book2 = createBook("jpa2", 50000, 500);
            em.persist(book2);

            Delivery delivery = createDelivery(member);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 30);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 40);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setStockQuantity(stockQuantity);
            book.setName(name);
            book.setPrice(price);
            return book;

        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

    }
}

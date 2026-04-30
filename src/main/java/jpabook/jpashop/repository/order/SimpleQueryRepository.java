package jpabook.jpashop.repository.order;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SimpleQueryRepository {
    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos(){
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.OrderSimpleQueryDto(" +
                        "o.id, o.member.name, o.orderDate, o.status, o.delivery.address)" +
                        " from Order o", OrderSimpleQueryDto.class
        ).getResultList();
    }
}

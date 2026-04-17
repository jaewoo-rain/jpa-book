package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item); // 새로 만들기
        }else{
            em.merge(item); // 기존의 것을 어딘가 가져와서 업데이트 비슷한 행위
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i ", Item.class)
                .getResultList();
    }
}

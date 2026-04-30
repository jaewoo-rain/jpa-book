package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.SimpleQueryRepository;
import jpabook.jpashop.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final SimpleQueryRepository simpleQueryRepository;

    @GetMapping("/api/v2/simple-orders")
    public Result ordersV1(){
        List<Order> orders = orderRepository.findAll();
        List<SimpleOrderDto> result = orders.stream().map(o -> new SimpleOrderDto(o)).toList();
        return new Result(result, "냥");
    }

    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3(){
        List<OrderSimpleQueryDto> orderDtos = simpleQueryRepository.findOrderDtos();
        return new Result(orderDtos, "2차");
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
        private String description;
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;


        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
}

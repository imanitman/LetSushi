package com.sushi.uddd.Service;

import com.sushi.uddd.Domain.Order;
import com.sushi.uddd.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order saveOrderInDb(Order order){
        return this.orderRepository.save(order);
    }

}

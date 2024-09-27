package com.sushi.uddd.Repository;

import com.sushi.uddd.Domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository <Order,Long >{
    Order save (Order order);

}

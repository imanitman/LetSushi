package com.sushi.uddd.Repository;

import com.sushi.uddd.Domain.Cart;
import com.sushi.uddd.Domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository  extends JpaRepository<Cart, Long> {

    Cart findById(long id);
    Cart save (Cart cart);
    void deleteById(long id);
}

package com.sushi.uddd.Repository;

import com.sushi.uddd.Domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Food save(Food food);
    Food findById(long id);
}

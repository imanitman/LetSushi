package com.sushi.uddd.Repository;

import com.sushi.uddd.Domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Food save(Food food);
    Food findById(long id);
    List<Food> findAll();
    List<Food> findByNameContainingIgnoreCase(String name);
}

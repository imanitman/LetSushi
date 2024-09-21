package com.sushi.uddd.Service;

import com.sushi.uddd.Domain.Food;
import com.sushi.uddd.Repository.FoodRepository;
import org.springframework.stereotype.Service;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    public FoodService (FoodRepository foodRepository){
        this.foodRepository = foodRepository;
    }
    public Food createNewFood(Food food){
        return this.foodRepository.save(food);
    }
    public Food fetchFoodById(long id){
        return this.foodRepository.findById(id);
    }
}

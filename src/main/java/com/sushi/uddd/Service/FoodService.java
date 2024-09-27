package com.sushi.uddd.Service;

import com.sushi.uddd.Domain.Food;
import com.sushi.uddd.Domain.Response.ResFoodDto;
import com.sushi.uddd.Repository.FoodRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService (FoodRepository foodRepository){
        this.foodRepository = foodRepository;
    }
    public Food createNewFood(Food food){
        return this.foodRepository.save(food);
    }
    public ResFoodDto convertToResFoodDto(Food food ){
        ResFoodDto resFoodDto = new ResFoodDto();
        resFoodDto.setId(food.getId());
        resFoodDto.setName(food.getName());
        resFoodDto.setLogo(food.getLogo());
        resFoodDto.setPrice(food.getPrice());
        resFoodDto.setDescription(food.getDescription());
        return resFoodDto;
    }
    public Food fetchFoodById(long id){
        return this.foodRepository.findById(id);
    }
    public List<ResFoodDto> convertToResFoodDtoList(List<Food> foodList){
        List<ResFoodDto> resFoodDtoList = new ArrayList<>();
        for (Food food : foodList) {
            resFoodDtoList.add(this.convertToResFoodDto(food));
        };
        return resFoodDtoList;
    }
    public List<ResFoodDto>  fetchAllFood(int page , int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Food> pageFood = this.foodRepository.findAll(pageable);
        List<Food> foodList = pageFood.getContent();
        List<ResFoodDto> resFoodDtoList = new ArrayList<>();
        resFoodDtoList= foodList.stream()
                .map(this::convertToResFoodDto)
                .toList();
        return resFoodDtoList;
    }
}

package com.sushi.uddd.Service;

import com.sushi.uddd.Domain.Food;
import com.sushi.uddd.Domain.Line;
import com.sushi.uddd.Domain.Response.ResLineDto;
import com.sushi.uddd.Repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LineService {
    private final LineRepository lineRepository;
    private final FoodService foodService;

    public Line saveLineInDb(Line line){
        return this.lineRepository.save(line);
    }
    public ResLineDto convertToLineDto(Line line){
        ResLineDto resLineDto = new ResLineDto();
        resLineDto.setId(line.getId());
        Food food = line.getFood();
        resLineDto.setResFoodDto(this.foodService.convertToResFoodDto(food));
        resLineDto.setQuantityStock(food.getStock().getQuantity());
        resLineDto.setQuantityLine(line.getQuantity());
        return resLineDto;
    }
}

package com.sushi.uddd.Controller;

import com.sushi.uddd.Domain.Food;
import com.sushi.uddd.Domain.Response.ResFoodDto;
import com.sushi.uddd.Service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class FoodController {
    private final String urlImage = "D://sushi/image/";
    private final FoodService foodService;

    public FoodController (FoodService foodService){
        this.foodService = foodService;
    }
    @GetMapping("/foods")
   public ResponseEntity<List<ResFoodDto>> pageListFood(@RequestParam (value = "page",defaultValue = "0") int page,@RequestParam (value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok().body(this.foodService.fetchAllFood(page,size));
   }
}

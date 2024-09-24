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

    @PostMapping("/foods")
    public ResponseEntity<String> pageCreateFood(@RequestParam ("name") String name,
                                               @RequestParam ("price") long price,
                                               @RequestParam ("description") String description,
                                               @RequestParam ("image") MultipartFile image){
        Food currenFood = new Food();
        currenFood.setName(name);
        currenFood.setPrice(price);
        currenFood.setDescription(description);
        if (image != null && !image.isEmpty()){
            try {
                String fileName = image.getOriginalFilename();
                if(fileName != null && !fileName.matches(".*\\.(png|jepg|jpg)$")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Chỉ chấp nhận file định dạng PNG, JPEG, JPG");
                }
                Path path  = Paths.get(urlImage+fileName);
                currenFood.setLogo(path.toString());
                Files.copy(image.getInputStream(), path);
                this.foodService.createNewFood(currenFood);
                return ResponseEntity.ok().body("done");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("file is invalid");
    }
    @GetMapping("/foods")
   public ResponseEntity<List<ResFoodDto>> pageListFood(){

   }
}

package com.sushi.uddd.Controller;

import com.sushi.uddd.Service.StockService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;
    @GetMapping("/create")
    public String  createStockPage(){
        stockService.createAllStock();
        return "done";
    }

}

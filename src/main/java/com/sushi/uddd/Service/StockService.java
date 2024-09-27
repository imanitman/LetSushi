package com.sushi.uddd.Service;

import com.sushi.uddd.Domain.Stock;
import com.sushi.uddd.Repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    public Stock createStock(Stock stock){
        return stockRepository.save(stock);
    }
    public Stock fetchStockById(long id){
        return stockRepository.findById(id);
    }
    public void createAllStock(){
        for (int i =1; i < 40; i++){
            Stock stock = new Stock();
            stock.setIdFood(i);
            stock.setQuantity(40);
            this.createStock(stock);
        }
    }
}

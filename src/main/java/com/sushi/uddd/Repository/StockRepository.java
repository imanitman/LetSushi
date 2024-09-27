package com.sushi.uddd.Repository;

import com.sushi.uddd.Domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository  extends JpaRepository<Stock, Long> {
    Stock save (Stock stock);
    Stock findById(long id);

}

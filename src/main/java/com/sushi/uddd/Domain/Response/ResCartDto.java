package com.sushi.uddd.Domain.Response;

import com.sushi.uddd.Domain.Line;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ResCartDto {
    private long idCart;
    private List<ResLineDto> ListLine;
    private long total_price;
}

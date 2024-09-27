package com.sushi.uddd.Domain.Response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResLineDto {
    private long id;
    private ResFoodDto resFoodDto;
    private int quantityStock;
    private int quantityLine;
}

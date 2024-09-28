package com.sushi.uddd.Domain.Response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResSearchDto {
    private long id;
    private String name;
    private String imageUrl;
    private double price;
}

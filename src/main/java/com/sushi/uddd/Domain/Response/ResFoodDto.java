package com.sushi.uddd.Domain.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResFoodDto {
    private String name;
    private long price;
    private String description;
    private String logo;
}

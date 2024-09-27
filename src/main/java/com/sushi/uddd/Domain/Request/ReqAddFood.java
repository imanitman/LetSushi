package com.sushi.uddd.Domain.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqAddFood {
    private long idUser;
    private long idFood;
    private int quantity;
}

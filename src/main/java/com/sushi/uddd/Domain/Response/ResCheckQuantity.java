package com.sushi.uddd.Domain.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCheckQuantity {
    private boolean isEnough;
    private String message;

    public void setIsEnough(boolean T){
        this.isEnough = T;
    }
}

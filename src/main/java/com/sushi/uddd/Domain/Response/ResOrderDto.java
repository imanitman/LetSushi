package com.sushi.uddd.Domain.Response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResOrderDto {
    private String message;
    private boolean isSuccess;

    public void setIsSuccess(boolean T){
        this.isSuccess = T;
    }
}

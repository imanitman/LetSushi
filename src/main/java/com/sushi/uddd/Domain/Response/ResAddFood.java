package com.sushi.uddd.Domain.Response;

import com.sushi.uddd.Domain.Line;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResAddFood {
    private boolean isSuccess;
    private String message;
    private long idLine;

    public void setIsSuccess(boolean T){
        isSuccess = T;
    }
}

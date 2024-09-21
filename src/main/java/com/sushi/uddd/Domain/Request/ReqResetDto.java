package com.sushi.uddd.Domain.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqResetDto {
    private String email;
    private String password;
}

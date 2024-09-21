package com.sushi.uddd.Domain.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqLoginDto {
    private String email;
    private String password;
}

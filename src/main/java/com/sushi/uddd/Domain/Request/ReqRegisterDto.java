package com.sushi.uddd.Domain.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqRegisterDto {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
}

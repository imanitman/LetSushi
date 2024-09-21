package com.sushi.uddd.Domain.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResRegisterDto {
    private String email;
    private String username;
    private String phoneNumber;
    private String role;
}

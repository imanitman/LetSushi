package com.sushi.uddd.Domain.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqForgetCheck {
    private String email;
    private String otp;
}

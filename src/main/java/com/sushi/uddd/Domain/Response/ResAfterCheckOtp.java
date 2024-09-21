package com.sushi.uddd.Domain.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResAfterCheckOtp {
    private String accessToken;
    private String email;
}

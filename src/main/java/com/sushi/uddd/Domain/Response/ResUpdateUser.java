package com.sushi.uddd.Domain.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResUpdateUser {
    private Long id;
    private String email;
    private String name;
    private String role;
    private String phoneNumber;
}

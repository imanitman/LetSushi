package com.sushi.uddd.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.sushi.uddd.Service.UserService;

@RestController
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

}

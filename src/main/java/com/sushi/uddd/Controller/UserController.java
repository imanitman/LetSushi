package com.sushi.uddd.Controller;

import com.sushi.uddd.Domain.Request.ReqUpdateUser;
import com.sushi.uddd.Domain.Response.ResDetailUser;
import com.sushi.uddd.Domain.Response.ResUpdateUser;
import com.sushi.uddd.Domain.User;
import com.sushi.uddd.Util.SecurityUtil;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.sushi.uddd.Service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/detail")
    public ResponseEntity<ResDetailUser> detailUser(){
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null ;
        ResDetailUser resDetailUser = new ResDetailUser();
        if (email != null){
            User currentUser = this.userService.fetchUserByEmail(email);
            if (currentUser != null){
                resDetailUser.setName(currentUser.getName());
                resDetailUser.setId(currentUser.getId());
                resDetailUser.setPhoneNumber(currentUser.getPhoneNumber());
                resDetailUser.setEmail(currentUser.getEmail());
                return ResponseEntity.ok().body(resDetailUser);
            }
            return ResponseEntity.badRequest().body(resDetailUser);
        }
        return ResponseEntity.ok().body(resDetailUser);
    }

    @PostMapping("/update")
    public ResponseEntity<ResUpdateUser> updatePage(@RequestBody ReqUpdateUser  reqUpdateUser) {
        String currentEmail = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null;
        ResUpdateUser resUpdateUser = new ResUpdateUser();
        if (currentEmail != null) {
            User user = this.userService.fetchUserByEmail(currentEmail);
            if (reqUpdateUser.getName() != null){
                user.setName(reqUpdateUser.getName());
                resUpdateUser.setName(reqUpdateUser.getName());
            }
            if (reqUpdateUser.getPhonenumber() != null){
                user.setPhoneNumber(reqUpdateUser.getPhonenumber());
                resUpdateUser.setPhoneNumber(reqUpdateUser.getPhonenumber());
            }
            this.userService.saveUserToDb(user);
            resUpdateUser.setRole(user.getRole());
            resUpdateUser.setId(user.getId());
            resUpdateUser.setEmail(user.getEmail());

            return ResponseEntity.ok().body(resUpdateUser);
        }
        return ResponseEntity.ok().body(resUpdateUser);
    }
}

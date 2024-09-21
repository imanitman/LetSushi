package com.sushi.uddd.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.sushi.uddd.Domain.User;
import com.sushi.uddd.Domain.Request.ReqForgetCheck;
import com.sushi.uddd.Domain.Request.ReqLoginDto;
import com.sushi.uddd.Domain.Request.ReqRegisterDto;
import com.sushi.uddd.Domain.Request.ReqResetDto;
import com.sushi.uddd.Domain.Response.ResAfterCheckOtp;
import com.sushi.uddd.Domain.Response.ResLoginDto;
import com.sushi.uddd.Domain.Response.ResRegisterDto;
import com.sushi.uddd.Service.EmailService;
import com.sushi.uddd.Service.UserService;
import com.sushi.uddd.Util.SecurityUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Duration;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailService emailService;

    public AuthController (UserService userService,AuthenticationManagerBuilder authenticationManagerBuilder,PasswordEncoder passwordEncoder,SecurityUtil securityUtil,
    RedisTemplate<String, String> redisTemplate,EmailService emailService){
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.passwordEncoder = passwordEncoder;
        this.securityUtil = securityUtil;
        this.redisTemplate = redisTemplate;
        this.emailService = emailService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResRegisterDto> registerPage(@RequestBody ReqRegisterDto reqRegisterDto) {
        User currentUser = this.userService.convertDtoToUser(reqRegisterDto);
        currentUser.setRole("USER");
        currentUser.setPassword(this.passwordEncoder.encode(reqRegisterDto.getPassword()));
        this.userService.saveUserToDb(currentUser);
        ResRegisterDto resRegisterDto = new ResRegisterDto();
        resRegisterDto.setEmail(currentUser.getEmail());
        resRegisterDto.setPhoneNumber(currentUser.getPhoneNumber());
        resRegisterDto.setRole(currentUser.getRole());
        resRegisterDto.setUsername(currentUser.getName());
        return ResponseEntity.ok().body(resRegisterDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDto> loginPage(@RequestBody ReqLoginDto reqLoginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(reqLoginDto.getEmail(), reqLoginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResLoginDto resLoginDto = new ResLoginDto();
        User currentUserDB = this.userService.fetchUserByEmail(reqLoginDto.getEmail());
        if (currentUserDB != null){
            ResLoginDto.UserLogin userLogin = new ResLoginDto.UserLogin();
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setId(currentUserDB.getId());
            userLogin.setName(currentUserDB.getName());
            userLogin.setRole(currentUserDB.getRole());
            resLoginDto.setUser(userLogin);
        }
        String accessToken = this.securityUtil.createAccessToken(currentUserDB.getEmail(), resLoginDto);
        resLoginDto.setAccessToken(accessToken);
        return ResponseEntity.ok(resLoginDto);
    }
    @PostMapping("/forget")
    public ResponseEntity<String> sendOtp(@RequestBody String email){
        String serverOtp = this.securityUtil.generateOtp();
        this.emailService.sendVerificationEmail(email,"Your otp",serverOtp);
        this.redisTemplate.opsForValue().set(email, serverOtp,Duration.ofMinutes(3));
        return ResponseEntity.ok().body(serverOtp);
    }
    @PostMapping("/forget/check")
    public ResponseEntity<ResAfterCheckOtp> checkOtpPage(@RequestBody ReqForgetCheck otpUser ) {
        ResAfterCheckOtp resAfterCheckOtp = new ResAfterCheckOtp();
        if (otpUser.getOtp() != null){
            String otp = this.redisTemplate.opsForValue().get(otpUser.getEmail());
            if (otpUser.getOtp() == otp){
                String accessForget = this.securityUtil.createForgetToken(otpUser.getEmail());
                resAfterCheckOtp.setAccessToken(accessForget);
                resAfterCheckOtp.setEmail(accessForget);
            }
            return ResponseEntity.ok().body(resAfterCheckOtp);
        }
        return ResponseEntity.badRequest().body(null);
    }
    @PostMapping("/forget/reset")
    public ResponseEntity<User> resetPassword(@RequestBody ReqResetDto resResetDto) {
        User currentUser = this.userService.fetchUserByEmail(resResetDto.getEmail());
        currentUser.setPassword(this.passwordEncoder.encode(resResetDto.getPassword()));
        return ResponseEntity.ok().body(currentUser);
    }
    //    @GetMapping("/getToken")
    //    public ResponseEntity<List<String>> getBothToken(){
    //        SecurityUtil
    //    }
    @PostMapping("/logout")
    public ResponseEntity<String> pageLogout(){
        return ResponseEntity.ok().body("success");
    }
}

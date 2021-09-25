package com.example.websocketwebflux.controller;

import com.example.websocketwebflux.dto.UserDTO;
import com.example.websocketwebflux.model.*;
import com.example.websocketwebflux.security.JWTUtil;
import com.example.websocketwebflux.service.AuthService;
import com.example.websocketwebflux.service.FirebaseTokenService;
import com.example.websocketwebflux.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
//@RequestMapping(value = "/auth")
public class UserController {

    private final UserServiceImpl userService;
    private final JWTUtil jwtUtil;
    private final FirebaseTokenService firebaseTokenService;
    private final AuthService authService;

    public UserController(UserServiceImpl userService, JWTUtil jwtUtil, FirebaseTokenService firebaseTokenService, AuthService authService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.firebaseTokenService = firebaseTokenService;
        this.authService = authService;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDTO> createUser(@RequestBody UserModel userModel) {
        return userService.createUser(userModel);
    }
/*
    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
        return userService.findByUsername(ar.getUsername())
            .filter(userDetails -> {
                return (userDetails != null) &&
                    (new Argon2PasswordEncoder().matches(ar.getPassword(), userDetails.getPassword()));
            })
            .map(userDetails -> {
                String token = jwtUtil.generateToken((CustomUserDetails) userDetails);
                Date expiredDate = jwtUtil.getExpirationDateFromToken(token);
                return ResponseEntity.ok(new AuthResponse(token, expiredDate));
            })
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }*/

   /* @PostMapping(value = "/login-firebase")
    public Mono<BaseResponse<Object>> loginFirebase(@RequestBody FirebaseToken firebaseToken) {
        return authService.loginFirebase(firebaseToken);
    }

    @PostMapping(value = "/sign-up-firebase")
    public Mono<BaseResponse<Object>> signUpFirebase(@RequestBody FirebaseToken firebaseToken) {
        return authService.signUpFirebase(firebaseToken);
    }*/
}


package com.example.websocketwebflux.controller;

import com.example.websocketwebflux.model.BaseResponse;
import com.example.websocketwebflux.model.FirebaseToken;
import com.example.websocketwebflux.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login-firebase")
    public Mono<BaseResponse<Object>> loginFirebase(@Valid @RequestBody FirebaseToken firebaseToken) {
        return authService.loginFirebase(firebaseToken);
    }

    @PostMapping("/sign-up-firebase")
    public Mono<BaseResponse<Object>> signUpFirebase(@Valid @RequestBody FirebaseToken firebaseToken) {
        return authService.signUpFirebase(firebaseToken);
    }
}

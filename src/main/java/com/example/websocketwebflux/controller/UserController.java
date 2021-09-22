package com.example.websocketwebflux.controller;

import com.example.websocketwebflux.dto.UserDTO;
import com.example.websocketwebflux.model.AuthRequest;
import com.example.websocketwebflux.model.AuthResponse;
import com.example.websocketwebflux.model.CustomUserDetails;
import com.example.websocketwebflux.model.UserModel;
import com.example.websocketwebflux.security.JWTUtil;
import com.example.websocketwebflux.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserServiceImpl userService;
    private final JWTUtil jwtUtil;
    public UserController(UserServiceImpl userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDTO> createUser(@RequestBody UserModel userModel){
        return userService.createUser(userModel);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
        return userService.findByUsername(ar.getUsername())
            .filter(userDetails -> {
                if (
                    (userDetails == null) ||
                        (!new Argon2PasswordEncoder().matches(ar.getPassword(), userDetails.getPassword()))
                )
                    return false;
                else
                    return true;
            })
            .map(userDetails -> ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken((CustomUserDetails) userDetails))))
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

}

package com.example.websocketwebflux.controller;

import com.example.websocketwebflux.dto.UserDTO;
import com.example.websocketwebflux.model.*;
import com.example.websocketwebflux.security.JWTUtil;
import com.example.websocketwebflux.service.FirebaseTokenService;
import com.example.websocketwebflux.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserServiceImpl userService;
    private final JWTUtil jwtUtil;
    private final FirebaseTokenService firebaseTokenService;
    public UserController(UserServiceImpl userService, JWTUtil jwtUtil, FirebaseTokenService firebaseTokenService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.firebaseTokenService = firebaseTokenService;
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
                return (userDetails != null) &&
                    (new Argon2PasswordEncoder().matches(ar.getPassword(), userDetails.getPassword()));
            })
            .map(userDetails -> {
                String token = jwtUtil.generateToken((CustomUserDetails) userDetails);
                Date expiredDate = jwtUtil.getExpirationDateFromToken(token);
                return ResponseEntity.ok(new AuthResponse(token, expiredDate));
            })
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

   /* @PostMapping(value = "/login-firebase")
    public Mono<ResponseEntity<AuthResponse>> loginFirebase(@RequestBody String firebaseToken){
        String uid = firebaseTokenService.getUidFromFirebaseToken(firebaseToken);
        CustomUserDetails userDetails = new CustomUserDetails(UserModel.builder().username(uid).role("USER").build());
        String token = jwtUtil.generateToken(userDetails);
        Date expiredDate = jwtUtil.getExpirationDateFromToken(token);
        return Mono.just(ResponseEntity.ok(new AuthResponse(token, expiredDate)))
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }*/

    @PostMapping(value = "/login-firebase")
    public Mono<BaseResponse<Object>> loginFirebase(@RequestBody String firebaseToken){
        String uid = firebaseTokenService.getUidFromFirebaseToken(firebaseToken);
        if (uid == null)
            return Mono.just(
                BaseResponse.builder()
                    .result(AuthResponse.builder().token(null).expiredDate(null))
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .errorMessage("Error!")
                    .build());
        else{
            CustomUserDetails userDetails = new CustomUserDetails(UserModel.builder().username(uid).role("USER").build());
            String token = jwtUtil.generateToken(userDetails);
            Date expiredDate = jwtUtil.getExpirationDateFromToken(token);
            AuthResponse authResponse = new AuthResponse(token, expiredDate);
            BaseResponse<Object> baseResponse = BaseResponse.builder().result(authResponse).code(HttpStatus.OK.value()).build();
            return Mono.just(baseResponse);
        }
    }

}

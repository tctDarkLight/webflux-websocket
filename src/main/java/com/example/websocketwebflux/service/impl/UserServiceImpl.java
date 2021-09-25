package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.dto.UserDTO;
import com.example.websocketwebflux.mapper.IUserMapper;
import com.example.websocketwebflux.model.CustomUserDetails;
import com.example.websocketwebflux.model.UserModel;
import com.example.websocketwebflux.repository.UserRepo;
import com.example.websocketwebflux.service.UserService;
import com.example.websocketwebflux.service.FirebaseTokenService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService, ReactiveUserDetailsService {

    private final UserRepo userRepo;
    private final IUserMapper userMapper;
    private final FirebaseTokenService firebaseTokenService;

    public UserServiceImpl(UserRepo userRepo, IUserMapper userMapper, FirebaseTokenService firebaseTokenService) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.firebaseTokenService = firebaseTokenService;
    }

    @Override
    public Mono<UserDTO> createUser(UserModel userModel) {
        userModel.setPassword(new Argon2PasswordEncoder().encode(userModel.getPassword()));
        return userRepo.save(userModel)
            .map(userMapper::toDTO);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepo.findByUsername(username).switchIfEmpty(Mono.defer(() -> {
            return Mono.error(new UsernameNotFoundException("User Not Found"));
        })).map(CustomUserDetails::new);
    }

    @Override
    public Mono<UserDTO> findUserByEmail(String email) {
        return userRepo.findByEmail(email)
            .map(userMapper::toDTO);
    }

    @Override
    public Mono<Boolean> isExistUser(String email) {
        return userRepo.findByEmail(email).hasElement();
    }

    @Override
    public Mono<UserDTO> createFirebaseUser(String firebaseToken) {
        String email = firebaseTokenService.getEmailFromFirebaseToken(firebaseToken);
        String[] splitEmail = email.split("@");

        //default username and password is username of email
        UserModel userModel =
            UserModel.builder()
                .username(splitEmail[0])
                .password(splitEmail[0])
                .email(email)
                .role("USER")
                .build();
        return this.createUser(userModel);
    }
}

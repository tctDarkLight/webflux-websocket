package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.dto.UserDTO;
import com.example.websocketwebflux.mapper.IUserMapper;
import com.example.websocketwebflux.model.CustomUserDetails;
import com.example.websocketwebflux.model.UserModel;
import com.example.websocketwebflux.repository.UserRepo;
import com.example.websocketwebflux.service.UserService;
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

    public UserServiceImpl(UserRepo userRepo, IUserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
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
}

package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.dto.UserDTO;
import com.example.websocketwebflux.exception.CustomResponseStatus;
import com.example.websocketwebflux.exception.PasswordInvalidException;
import com.example.websocketwebflux.mapper.UserMapper;
import com.example.websocketwebflux.model.CustomUserDetails;
import com.example.websocketwebflux.model.RoleUser;
import com.example.websocketwebflux.model.UserModel;
import com.example.websocketwebflux.repository.UserRepo;
import com.example.websocketwebflux.security.RandomPasswordGenerator;
import com.example.websocketwebflux.service.UserService;
import com.example.websocketwebflux.service.FirebaseTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService, ReactiveUserDetailsService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final FirebaseTokenService firebaseTokenService;
    private final RandomPasswordGenerator randomPasswordGenerator;
    /*@Value("${springbootwebflux.validation.regex}")
    private String regexPassword;
*/
    public UserServiceImpl(UserRepo userRepo, UserMapper userMapper, FirebaseTokenService firebaseTokenService, RandomPasswordGenerator randomPasswordGenerator) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.firebaseTokenService = firebaseTokenService;
        this.randomPasswordGenerator = randomPasswordGenerator;
    }

    @Override
    public Mono<UserDTO> createUser(UserModel userModel) {
        userModel.setPassword(new Argon2PasswordEncoder().encode(userModel.getPassword()));
        userModel.setRole(RoleUser.userRole);
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
        String randomPassword = randomPasswordGenerator.generatePassayPassword();

        UserModel userModel =
            UserModel.builder()
                .username(splitEmail[0])
                .password(randomPassword)
                .email(email)
                .role(RoleUser.userRole)
                .build();
        return this.createUser(userModel);
       /* if (randomPassword.matches(regexPassword)){
            UserModel userModel =
                UserModel.builder()
                    .username(splitEmail[0])
                    .password(randomPassword)
                    .email(email)
                    .role(RoleUser.userRole)
                    .build();
            return this.createUser(userModel);
        }else {
            return Mono.error(new PasswordInvalidException());
        }*/

    }
}

package com.example.websocketwebflux.security;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        String authToken = authentication.getCredentials().toString();
        String username = jwtUtil.getUsernameFromToken(authToken);

        Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
        //List<String> rolesMap = claims.get("role", List.class);
        String rolesMap = claims.get("role", String.class);
        return Mono.just(new UsernamePasswordAuthenticationToken(
            username,
            null,
            Collections.singleton(new SimpleGrantedAuthority(rolesMap)))
        );
    }

}

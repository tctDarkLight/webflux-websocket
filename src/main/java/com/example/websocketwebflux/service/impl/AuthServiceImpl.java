package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.security.JWTUtil;
import com.example.websocketwebflux.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JWTUtil jwtUtil;

    public AuthServiceImpl(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String getUsernameFromToken(String token) {
        return jwtUtil.getUsernameFromToken(token);
    }
}

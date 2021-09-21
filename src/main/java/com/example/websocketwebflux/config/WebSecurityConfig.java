package com.example.websocketwebflux.config;

import com.example.websocketwebflux.security.AuthenticationManager;
import com.example.websocketwebflux.security.SecurityContextRepository;
import com.example.websocketwebflux.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private Logger logger;
    private UserServiceImpl userService;

    public WebSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange()
            .anyExchange()
            .permitAll()
            .and()
            .csrf().disable()
            .build();
    }
}

package com.example.apicontrolefinanceiro.config;

import com.example.apicontrolefinanceiro.service.JwtService;
import org.springframework.web.filter.OncePerRequestFilter;

public abstract class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }
}

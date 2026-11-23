package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.dto.LoginRequestDTO;
import com.example.apicontrolefinanceiro.dto.LoginResponseDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){

        UsernamePasswordAuthenticationToken authenticationToken = new  UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),
                loginRequestDTO.getSenha());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

         return new LoginResponseDTO(jwtService.gerarToken(authentication.getName()));

    }
}

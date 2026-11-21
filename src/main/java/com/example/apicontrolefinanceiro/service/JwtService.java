
package com.example.apicontrolefinanceiro.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret;

    private SecretKey gerarChave() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String gerarToken(String email) {
        Instant agora = Instant.now();

        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(agora))
                .expiration(Date.from(agora.plusSeconds(7200)))
                .signWith(gerarChave())
                .compact();
    }

    public String extrairEmail(String token) {
        return obterClaims(token).getSubject();
    }

    public boolean validarToken(String token) {
        try {
            obterClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims obterClaims(String token) {
        return Jwts.parser()
                .verifyWith(gerarChave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

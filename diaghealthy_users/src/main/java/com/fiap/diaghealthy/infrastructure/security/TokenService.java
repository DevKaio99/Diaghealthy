package com.fiap.diaghealthy.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fiap.diaghealthy.infrastructure.security.exceptions.TokenGenerationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(UserDetails userDetails) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            String role = userDetails.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();

            var tokenBuilder = JWT.create()
                    .withIssuer("Diaghealthy")
                    .withSubject(userDetails.getUsername())
                    .withClaim("role", role)
                    .withExpiresAt(gerarDataExpiracaoToken());

            if (userDetails instanceof CustomUserDetails customUserDetails) {
                tokenBuilder.withClaim("id", customUserDetails.getId().toString());
            }

            return tokenBuilder.sign(algoritmo);

        } catch (JWTCreationException exception) {
            throw new TokenGenerationException(
                    "Erro ao gerar o token",
                    exception
            );
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer("Diaghealthy")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    public String getRole(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer("Diaghealthy")
                    .build()
                    .verify(token)
                    .getClaim("role")
                    .asString();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant gerarDataExpiracaoToken() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
package com.diaghealthy_history.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private DecodedJWT verificarToken(String token) {

        Algorithm algoritmo = Algorithm.HMAC256(secret);

        return JWT.require(algoritmo)
                .withIssuer("Diaghealthy")
                .build()
                .verify(token);
    }

    public String getSubject(String token) {
        return verificarToken(token).getSubject();
    }

    public String getRole(String token) {
        return verificarToken(token)
                .getClaim("role")
                .asString();
    }

    public UUID getId(String token) {
        String id = verificarToken(token)
                .getClaim("id")
                .asString();

        return id != null ? UUID.fromString(id) : null;
    }
}

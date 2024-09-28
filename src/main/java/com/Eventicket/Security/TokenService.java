package com.Eventicket.Security;

import com.Eventicket.Entities.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${secreto}")
    private String secreto;

    public String generateToken(UserEntity user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secreto);

            String token = JWT.create()
                    .withIssuer("api-eventicket")
                    .withSubject(user.getEmail())
                    .withSubject(user.getCpf())
                    .withExpiresAt(generateeExpirationDate())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    private Instant generateeExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validarToken(String token) {
        try {
            Algorithm algoritimo = Algorithm.HMAC256(secreto);

            return JWT.require(algoritimo)
                    .withIssuer("api-eventicket")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException VE) {
            return null;
        }
    }
}

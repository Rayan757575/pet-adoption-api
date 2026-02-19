package com.rayancatapreta.pet_adoption_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rayancatapreta.pet_adoption_api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String ISSUER = "pet-adoption-api"; // name of who is creating the token
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // return token
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail()) // the user who is receiving the token
                    .withExpiresAt(genExpirationDate()) // token's time expiration
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            log.error("Error generating token for user {}: ", user.getEmail(), exception);
            throw new RuntimeException("Error generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // verify the token
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            log.warn("Validation attempt with invalid token");
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // set 2 hours to time expiration
    }
}
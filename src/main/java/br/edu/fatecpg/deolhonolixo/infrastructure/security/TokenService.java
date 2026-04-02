package br.edu.fatecpg.deolhonolixo.infrastructure.security;

import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserJpaEntity user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withClaim("id", user.getId())
                    .withClaim("roles", user.getRoles().stream().map(Enum::name).toList())
                    .withSubject(user.getEmail())
                    .withExpiresAt(LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00")))
                    .withIssuedAt(Instant.now())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error While Creating Token", exception);
        }
    }

    public Optional<JWTUserData> validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decode = JWT.require(algorithm).build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .id(decode.getClaim("id").asLong())
                    .email(decode.getSubject())
                    .roles(decode.getClaim("roles").asList(String.class))
                    .build()
            );
        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }
}
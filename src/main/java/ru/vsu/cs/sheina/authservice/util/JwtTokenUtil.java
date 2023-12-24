package ru.vsu.cs.sheina.authservice.util;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import com.auth0.jwt.JWT;
import ru.vsu.cs.sheina.authservice.dto.UserDTO;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String signature;

    @Value("${jwt.key}")
    private String key;

    public String generateToken(UserDTO userDTO){
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("id", userDTO.getId().toString())
                .withClaim("nickname", userDTO.getNickname())
                .withClaim("email", userDTO.getEmail())
                .withClaim("key", key)
                .withIssuedAt(new Date())
                .withIssuer("auth-service")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(signature));
    }

    public UserDTO retrieveClaims(String token) throws JWTVerificationException {
        token = token.substring(7);
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(signature))
                .withSubject("User details")
                .withIssuer("auth-service")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return new UserDTO(UUID.fromString(jwt.getClaim("id").asString()),
                            jwt.getClaim("nickname").asString(),
                            jwt.getClaim("email").asString());
    }
}

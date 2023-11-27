package ru.vsu.cs.sheina.authservice.util;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import com.auth0.jwt.JWT;
import ru.vsu.cs.sheina.authservice.dto.JwtDTO;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String signature;

    public String generateToken(JwtDTO jwtDTO){
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("nickname", jwtDTO.getNickname())
                .withClaim("email", jwtDTO.getEmail())
                .withIssuedAt(new Date())
                .withIssuer("auth-service")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(signature));
    }

    public JwtDTO retrieveClaims(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(signature))
                .withSubject("User details")
                .withIssuer("auth-service")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return new JwtDTO(jwt.getClaim("nickname").asString(), jwt.getClaim("email").asString());
    }
}

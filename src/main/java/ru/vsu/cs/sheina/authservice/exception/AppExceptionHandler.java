package ru.vsu.cs.sheina.authservice.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleException(AppException appException) {
        return ResponseEntity.status(appException.getStatus()).body(appException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleJwtException(JWTVerificationException jwtVerificationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad token");
    }
}

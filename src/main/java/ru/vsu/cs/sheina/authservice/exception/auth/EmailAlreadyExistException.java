package ru.vsu.cs.sheina.authservice.exception.auth;

import org.springframework.http.HttpStatus;
import ru.vsu.cs.sheina.authservice.exception.AppException;

public class EmailAlreadyExistException extends AppException {
    public EmailAlreadyExistException() {
        super("This email is already in use", HttpStatus.CONFLICT);
    }
}

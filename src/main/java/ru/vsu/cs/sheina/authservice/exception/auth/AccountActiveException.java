package ru.vsu.cs.sheina.authservice.exception.auth;

import org.springframework.http.HttpStatus;
import ru.vsu.cs.sheina.authservice.exception.AppException;

public class AccountActiveException extends AppException {
    public AccountActiveException() {
        super("Account has already been activated", HttpStatus.BAD_REQUEST);
    }
}

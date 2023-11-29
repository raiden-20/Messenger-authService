package ru.vsu.cs.sheina.authservice.exception.auth;

import org.springframework.http.HttpStatus;
import ru.vsu.cs.sheina.authservice.exception.AppException;

public class AccountBlockedException extends AppException {
    public AccountBlockedException() {
        super("Account has already been blocked", HttpStatus.FORBIDDEN);
    }
}

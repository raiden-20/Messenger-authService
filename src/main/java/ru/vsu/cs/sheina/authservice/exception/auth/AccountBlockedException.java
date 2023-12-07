package ru.vsu.cs.sheina.authservice.exception.auth;

import org.springframework.http.HttpStatus;
import ru.vsu.cs.sheina.authservice.exception.AppException;

import java.util.UUID;

public class AccountBlockedException extends AppException {
    public AccountBlockedException(UUID id) {
        super(id.toString(), HttpStatus.FORBIDDEN);
    }
}

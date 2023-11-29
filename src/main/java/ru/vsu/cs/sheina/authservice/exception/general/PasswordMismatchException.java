package ru.vsu.cs.sheina.authservice.exception.general;

import org.springframework.http.HttpStatus;
import ru.vsu.cs.sheina.authservice.exception.AppException;

public class PasswordMismatchException extends AppException {
    public PasswordMismatchException() {
        super("Password mismatch", HttpStatus.BAD_REQUEST);
    }
}

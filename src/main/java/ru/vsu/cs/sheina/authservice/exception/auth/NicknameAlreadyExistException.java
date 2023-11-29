package ru.vsu.cs.sheina.authservice.exception.auth;

import org.springframework.http.HttpStatus;
import ru.vsu.cs.sheina.authservice.exception.AppException;

public class NicknameAlreadyExistException extends AppException {
    public NicknameAlreadyExistException() {
        super("This nickname is already in use", HttpStatus.CONFLICT);
    }
}

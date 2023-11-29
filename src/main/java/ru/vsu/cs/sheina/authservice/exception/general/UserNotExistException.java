package ru.vsu.cs.sheina.authservice.exception.general;

import org.springframework.http.HttpStatus;
import ru.vsu.cs.sheina.authservice.exception.AppException;

public class UserNotExistException extends AppException {
    public UserNotExistException() {
        super("User doesn't exist", HttpStatus.BAD_REQUEST);
    }
}

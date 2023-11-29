package ru.vsu.cs.sheina.authservice.exception.general;

import org.springframework.http.HttpStatus;
import ru.vsu.cs.sheina.authservice.exception.AppException;

public class CodeNotMatchException extends AppException {
    public CodeNotMatchException() {
        super("Code doesn't match", HttpStatus.BAD_REQUEST);
    }
}

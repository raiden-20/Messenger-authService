package ru.vsu.cs.sheina.authservice.exception.general;

import org.springframework.http.HttpStatus;
import ru.vsu.cs.sheina.authservice.exception.AppException;

public class CodeNotRelevantException extends AppException {
    public CodeNotRelevantException() {
        super("The code is not relevant", HttpStatus.BAD_REQUEST);
    }
}

package ru.vsu.cs.sheina.authservice.exception.general;

import org.springframework.http.HttpStatus;
import ru.vsu.cs.sheina.authservice.exception.AppException;

public class PasswordAlreadyChangedException extends AppException {
    public PasswordAlreadyChangedException() {
        super("The password was changed less than 5 minutes ago, please try again later", HttpStatus.CONFLICT);
    }
}

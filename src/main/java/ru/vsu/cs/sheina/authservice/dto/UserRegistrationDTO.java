package ru.vsu.cs.sheina.authservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRegistrationDTO {

    String email;
    String nickname;
    String password;
    String confirmPassword;
}

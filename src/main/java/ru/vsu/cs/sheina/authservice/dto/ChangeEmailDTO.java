package ru.vsu.cs.sheina.authservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChangeEmailDTO {
    String password;
    String newEmail;
}

package ru.vsu.cs.sheina.authservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ConfirmPasswordDTO {

    Integer oneTimeCode;
    String newPassword;
}

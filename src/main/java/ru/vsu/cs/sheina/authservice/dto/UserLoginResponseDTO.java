package ru.vsu.cs.sheina.authservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UserLoginResponseDTO {

    UUID id;
    String token;
}

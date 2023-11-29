package ru.vsu.cs.sheina.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {

    UUID id;
    String nickname;
    String email;
}

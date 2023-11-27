package ru.vsu.cs.sheina.authservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class JwtDTO {
    String nickname;
    String email;

    public JwtDTO(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}

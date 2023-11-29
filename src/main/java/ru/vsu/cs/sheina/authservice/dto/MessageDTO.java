package ru.vsu.cs.sheina.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.cs.sheina.authservice.dto.enums.TypeLetter;

@Data
@AllArgsConstructor
public class MessageDTO {

    String data;
    String email;
    TypeLetter type;
}

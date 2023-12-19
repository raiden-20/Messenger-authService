package ru.vsu.cs.sheina.authservice.dto.field;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class IdDTO {
    UUID id;
}

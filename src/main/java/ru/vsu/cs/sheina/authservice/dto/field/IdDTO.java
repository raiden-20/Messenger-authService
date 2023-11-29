package ru.vsu.cs.sheina.authservice.dto.field;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class IdDTO {
    UUID id;
}

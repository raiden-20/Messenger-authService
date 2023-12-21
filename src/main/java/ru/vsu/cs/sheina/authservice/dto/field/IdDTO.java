package ru.vsu.cs.sheina.authservice.dto.field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class IdDTO {
    UUID id;
}

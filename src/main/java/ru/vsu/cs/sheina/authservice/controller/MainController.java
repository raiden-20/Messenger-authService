package ru.vsu.cs.sheina.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.sheina.authservice.dto.UserLoginDTO;
import ru.vsu.cs.sheina.authservice.dto.UserLoginResponseDTO;
import ru.vsu.cs.sheina.authservice.dto.UserRegistrationDTO;
import ru.vsu.cs.sheina.authservice.dto.field.IdDTO;
import ru.vsu.cs.sheina.authservice.dto.field.PasswordDTO;
import ru.vsu.cs.sheina.authservice.service.AuthService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final AuthService authService;

    @PostMapping("/auth/registration")
    @CrossOrigin
    public ResponseEntity<?> registration(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        UUID id = authService.createUser(userRegistrationDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    @CrossOrigin
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        UserLoginResponseDTO userLoginResponseDTO = authService.login(userLoginDTO);
        return new ResponseEntity<>(userLoginResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/auth/block")
    @CrossOrigin
    public ResponseEntity<?> block(@RequestBody PasswordDTO passwordDTO,
                                   @RequestHeader("Authorization") String token) {
        authService.blockAccount(passwordDTO, token);
        return new ResponseEntity<>("Account is blocked", HttpStatus.OK);
    }

    @PutMapping("/auth/active/account")
    @CrossOrigin
    public ResponseEntity<?> activeAccount(@RequestBody IdDTO idDTO) {
        authService.activeAccount(idDTO);
        return new ResponseEntity<>("Account activated", HttpStatus.OK);
    }
}

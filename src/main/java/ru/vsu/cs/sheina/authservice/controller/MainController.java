package ru.vsu.cs.sheina.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.sheina.authservice.dto.UserLoginDTO;
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
    public ResponseEntity<?> registration(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        authService.createUser(userRegistrationDTO);
        return new ResponseEntity<>("Check your mailbox to activate your account", HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        String token = authService.createToken(userLoginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PutMapping("/auth/block")
    public ResponseEntity<?> block(@RequestBody PasswordDTO passwordDTO,
                                   @RequestHeader("Authorization") String token) {
        authService.blockAccount(passwordDTO, token);
        return new ResponseEntity<>("Account is blocked", HttpStatus.OK);
    }

    @PutMapping("/auth/active/account")
    public ResponseEntity<?> activeAccount(@RequestBody IdDTO idDTO) {
        authService.activeAccount(idDTO);
        return new ResponseEntity<>("Account activated", HttpStatus.OK);
    }
}

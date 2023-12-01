package ru.vsu.cs.sheina.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.sheina.authservice.dto.*;
import ru.vsu.cs.sheina.authservice.dto.field.EmailDTO;
import ru.vsu.cs.sheina.authservice.dto.field.NicknameDTO;
import ru.vsu.cs.sheina.authservice.dto.field.PasswordDTO;
import ru.vsu.cs.sheina.authservice.service.AuthService;
import ru.vsu.cs.sheina.authservice.service.DataService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChangeController {

    private final DataService dataService;

    @PostMapping("/auth/change/nickname")
    @CrossOrigin
    public ResponseEntity<?> changeNickname(@RequestBody NicknameDTO nicknameDTO,
                                            @RequestHeader("Authorization") String token) {
        dataService.changeNickname(nicknameDTO, token);
        return new ResponseEntity<>("Nickname changed", HttpStatus.OK);
    }
    @PostMapping("/auth/change/email")
    @CrossOrigin
    public ResponseEntity<?> changeEmail(@RequestBody ChangeEmailDTO changeEmailDTO,
                                         @RequestHeader("Authorization") String token) {
        dataService.changeEmail(changeEmailDTO, token);
        return new ResponseEntity<>("Check your mailbox to confirm email", HttpStatus.OK);
    }

    @PutMapping("/auth/confirm/email")
    @CrossOrigin
    public ResponseEntity<?> confirmEmail(@RequestBody EmailDTO emailDTO,
                                          @RequestHeader("Authorization") String token) {
        dataService.confirmEmail(emailDTO, token);
        return new ResponseEntity<>("Email confirmed", HttpStatus.OK);
    }

    @PostMapping("/auth/change/password")
    @CrossOrigin
    public ResponseEntity<?> changePassword(@RequestBody PasswordDTO passwordDTO,
                                            @RequestHeader("Authorization") String token) {
        dataService.changePassword(passwordDTO, token);
        return new ResponseEntity<>("Check your mailbox to confirm new password", HttpStatus.OK);
    }

    @PutMapping("/auth/confirm/password")
    @CrossOrigin
    public ResponseEntity<?> confirmPassword(@RequestBody ConfirmPasswordDTO confirmPasswordDTO,
                                             @RequestHeader("Authorization") String token) {
        dataService.confirmPassword(confirmPasswordDTO, token);
        return new ResponseEntity<>("Password changed", HttpStatus.OK);
    }
    @PutMapping("/auth/forget/password")
    @CrossOrigin
    public ResponseEntity<?> forgetPassword(@RequestBody EmailDTO emailDTO) {
        dataService.createNewPass(emailDTO);
        return new ResponseEntity<>("Check your mailbox", HttpStatus.OK);
    }

    @GetMapping("/auth/data/{id}")
    @CrossOrigin
    public ResponseEntity<?> getAuthData(@PathVariable UUID id) {
        UserDTO userDTO = dataService.getUserData(id);
        return ResponseEntity.ok(userDTO);
    }
}

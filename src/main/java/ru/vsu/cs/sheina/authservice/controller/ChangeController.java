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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ChangeController {

    private final DataService dataService;

    @PostMapping("/change/nickname")
    @CrossOrigin
    public ResponseEntity<?> changeNickname(@RequestBody NicknameDTO nicknameDTO,
                                            @RequestHeader("Authorization") String token) {
        String newToken = dataService.changeNickname(nicknameDTO, token);
        return new ResponseEntity<>(newToken, HttpStatus.OK);
    }
    @PostMapping("/change/email")
    @CrossOrigin
    public ResponseEntity<?> changeEmail(@RequestBody ChangeEmailDTO changeEmailDTO,
                                         @RequestHeader("Authorization") String token) {
        dataService.changeEmail(changeEmailDTO, token);
        return new ResponseEntity<>("Check your mailbox to confirm email", HttpStatus.OK);
    }

    @PutMapping("/confirm/email")
    @CrossOrigin
    public ResponseEntity<?> confirmEmail(@RequestBody EmailDTO emailDTO,
                                          @RequestHeader("Authorization") String token) {
        String newToken = dataService.confirmEmail(emailDTO, token);
        return new ResponseEntity<>(newToken, HttpStatus.OK);
    }

    @PostMapping("/change/password")
    @CrossOrigin
    public ResponseEntity<?> changePassword(@RequestBody PasswordDTO passwordDTO,
                                            @RequestHeader("Authorization") String token) {
        dataService.changePassword(passwordDTO, token);
        return new ResponseEntity<>("Check your mailbox to confirm new password", HttpStatus.OK);
    }

    @PutMapping("/confirm/password")
    @CrossOrigin
    public ResponseEntity<?> confirmPassword(@RequestBody ConfirmPasswordDTO confirmPasswordDTO,
                                             @RequestHeader("Authorization") String token) {
        dataService.confirmPassword(confirmPasswordDTO, token);
        return new ResponseEntity<>("Password changed", HttpStatus.OK);
    }
    @PutMapping("/forget/password")
    @CrossOrigin
    public ResponseEntity<?> forgetPassword(@RequestBody EmailDTO emailDTO) {
        dataService.createNewPass(emailDTO);
        return new ResponseEntity<>("Check your mailbox", HttpStatus.OK);
    }
}

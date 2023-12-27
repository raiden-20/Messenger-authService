package ru.vsu.cs.sheina.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.sheina.authservice.dto.ChangeEmailDTO;
import ru.vsu.cs.sheina.authservice.dto.ConfirmPasswordDTO;
import ru.vsu.cs.sheina.authservice.dto.UserDTO;
import ru.vsu.cs.sheina.authservice.dto.field.EmailDTO;
import ru.vsu.cs.sheina.authservice.dto.field.NicknameDTO;
import ru.vsu.cs.sheina.authservice.dto.field.PasswordDTO;
import ru.vsu.cs.sheina.authservice.entity.ServiceDataEntity;
import ru.vsu.cs.sheina.authservice.entity.UserEntity;
import ru.vsu.cs.sheina.authservice.exception.auth.EmailAlreadyExistException;
import ru.vsu.cs.sheina.authservice.exception.auth.NicknameAlreadyExistException;
import ru.vsu.cs.sheina.authservice.exception.general.*;
import ru.vsu.cs.sheina.authservice.repository.ServiceDataRepository;
import ru.vsu.cs.sheina.authservice.repository.UserCredentialsRepository;
import ru.vsu.cs.sheina.authservice.util.JwtTokenUtil;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataService {

    private final AuthService authService;
    private final UserCredentialsRepository userCredentialsRepository;
    private final ServiceDataRepository serviceDataRepository;
    private final MailSender mailSender;
    private final JwtTokenUtil jwtTokenUtil;
    private final long CHANGE_PASSWORD_TIME = 5 * 60 * 1000;
    private final long CODE_LIFETIME = 2 * 60 * 1000;
    private final Random random = new Random();

    public UserDTO getUserData(UUID id) {
        UserEntity userEntity = userCredentialsRepository.findById(id).orElseThrow(UserNotExistException::new);
        return new UserDTO(userEntity.getId(), userEntity.getNickname(), userEntity.getEmail());
    }

    public void createNewPass(EmailDTO emailDTO) {
        UserEntity userEntity = userCredentialsRepository.findByEmail(emailDTO.getEmail()).orElseThrow(UserNotExistException::new);
        if (userEntity.getPasswordDate().getTime() + CHANGE_PASSWORD_TIME >= new Timestamp(System.currentTimeMillis()).getTime()) {
            throw new PasswordAlreadyChangedException();
        }

        String newPass = generateRandomPass();
        userEntity.setPassword(newPass.hashCode());
        userEntity.setPasswordDate(new Timestamp(System.currentTimeMillis()));
        userCredentialsRepository.save(userEntity);
        //mailSender.sendNewPassMessage(emailDTO.getEmail(), newPass);
    }

    public void changePassword(PasswordDTO passwordDTO, String token) {
        UserDTO userDTO = jwtTokenUtil.retrieveClaims(token);
        UserEntity userEntity = userCredentialsRepository.findById(userDTO.getId()).orElseThrow(UserNotExistException::new);

        if (!userEntity.getPassword().equals(passwordDTO.getPassword().hashCode())) {
            throw new PasswordMismatchException();
        }
        if (userEntity.getPasswordDate().getTime() + CHANGE_PASSWORD_TIME >= new Timestamp(System.currentTimeMillis()).getTime()) {
            throw new PasswordAlreadyChangedException();
        }
        Integer code = generateCode();
        ServiceDataEntity serviceDataEntity = new ServiceDataEntity();
        serviceDataEntity.setId(userEntity.getId());
        serviceDataEntity.setKey(code);
        serviceDataEntity.setDate(new Timestamp(System.currentTimeMillis()));
        serviceDataRepository.save(serviceDataEntity);

        //mailSender.sendConfirmNewPassMessage(userEntity.getEmail(), code.toString());

    }
    public void confirmPassword(ConfirmPasswordDTO confirmPasswordDTO, String token) {
        UserDTO userDTO = jwtTokenUtil.retrieveClaims(token);
        UserEntity userEntity = userCredentialsRepository.findById(userDTO.getId()).orElseThrow(UserNotExistException::new);
        ServiceDataEntity serviceDataEntity = serviceDataRepository.findById(userEntity.getId()).orElseThrow(UserNotExistException::new);

        if (!serviceDataEntity.getKey().equals(confirmPasswordDTO.getOneTimeCode())) {
            throw new CodeNotMatchException();
        }
        if (serviceDataEntity.getDate().getTime() + CODE_LIFETIME < new Timestamp(System.currentTimeMillis()).getTime()){
            throw new CodeNotRelevantException();
        }

        userEntity.setPassword(confirmPasswordDTO.getNewPassword().hashCode());
        userCredentialsRepository.save(userEntity);
    }

    public String confirmEmail(EmailDTO emailDTO, String token) {
        UserDTO userDTO = jwtTokenUtil.retrieveClaims(token);
        UserEntity userEntity = userCredentialsRepository.findById(userDTO.getId()).orElseThrow(UserNotExistException::new);
        userEntity.setEmail(emailDTO.getEmail());
        userCredentialsRepository.save(userEntity);

        return authService.createToken(userEntity);
    }

    public void changeEmail(ChangeEmailDTO changeEmailDTO, String token) {
        UserDTO userDTO = jwtTokenUtil.retrieveClaims(token);
        UserEntity userEntity = userCredentialsRepository.findById(userDTO.getId()).orElseThrow(UserNotExistException::new);

        if (!userEntity.getPassword().equals(changeEmailDTO.getPassword().hashCode())) {
            throw new PasswordMismatchException();
        }
        if (userCredentialsRepository.existsByEmail(changeEmailDTO.getNewEmail())) {
            throw new EmailAlreadyExistException();
        }

        //mailSender.sendConfirmEmailMessage(changeEmailDTO.getNewEmail());
    }

    public String changeNickname(NicknameDTO nicknameDTO, String token) {
        String newNickname = nicknameDTO.getNickname();
        UserDTO userDTO = jwtTokenUtil.retrieveClaims(token);
        UserEntity userEntity = userCredentialsRepository.findById(userDTO.getId()).orElseThrow(UserNotExistException::new);

        if (nicknameDTO.getNickname().equals(userEntity.getNickname())) {
            token = token.substring(7);
            return token;
        }

        if (userCredentialsRepository.existsByNickname(newNickname)) {
            throw new NicknameAlreadyExistException();
        }

        userEntity.setNickname(newNickname);
        userCredentialsRepository.save(userEntity);

        return authService.createToken(userEntity);
    }

    private String generateRandomPass() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 15;

        String generatedPass = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedPass;
    }

    private Integer generateCode() {
        int min = 10000;
        int max = 99999;
        return min + random.nextInt(max - min + 1);
    }
}

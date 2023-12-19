package ru.vsu.cs.sheina.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.sheina.authservice.dto.UserDTO;
import ru.vsu.cs.sheina.authservice.dto.UserLoginDTO;
import ru.vsu.cs.sheina.authservice.dto.UserLoginResponseDTO;
import ru.vsu.cs.sheina.authservice.dto.UserRegistrationDTO;
import ru.vsu.cs.sheina.authservice.dto.field.IdDTO;
import ru.vsu.cs.sheina.authservice.dto.field.PasswordDTO;
import ru.vsu.cs.sheina.authservice.entity.UserEntity;
import ru.vsu.cs.sheina.authservice.exception.auth.AccountActiveException;
import ru.vsu.cs.sheina.authservice.exception.auth.EmailAlreadyExistException;
import ru.vsu.cs.sheina.authservice.exception.auth.NicknameAlreadyExistException;
import ru.vsu.cs.sheina.authservice.exception.auth.AccountBlockedException;
import ru.vsu.cs.sheina.authservice.exception.general.PasswordMismatchException;
import ru.vsu.cs.sheina.authservice.exception.general.UserNotExistException;
import ru.vsu.cs.sheina.authservice.repository.UserCredentialsRepository;
import ru.vsu.cs.sheina.authservice.util.JwtTokenUtil;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialsRepository userCredentialsRepository;
    private final MailSender mailSender;
    private final SocialSender socialSender;
    private final JwtTokenUtil jwtTokenUtil;

    public UUID createUser(UserRegistrationDTO userRegistrationDTO) {
        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }
        if (userCredentialsRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            throw new EmailAlreadyExistException();
        }
        if (userCredentialsRepository.existsByNickname(userRegistrationDTO.getNickname())) {
            throw new NicknameAlreadyExistException();
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userRegistrationDTO.getEmail());
        userEntity.setNickname(userRegistrationDTO.getNickname());
        userEntity.setPassword(userRegistrationDTO.getPassword().hashCode());
        userEntity.setPasswordDate(new Timestamp(System.currentTimeMillis()));
        userEntity.setAccountStatus(false);
        //todo userMapper

        userCredentialsRepository.save(userEntity);

        socialSender.sendCreateUserRequest(userEntity.getId());
        mailSender.sendActivateMessage(userRegistrationDTO.getEmail());

        return userEntity.getId();
    }

    public void activeAccount(IdDTO idDTO) {
        UserEntity userEntity = userCredentialsRepository.findById(idDTO.getId()).orElseThrow(UserNotExistException::new);
        if (userEntity.getAccountStatus()) {
            throw new AccountActiveException();
        }
        userEntity.setAccountStatus(true);
        userCredentialsRepository.save(userEntity);
    }

    public UserLoginResponseDTO login(UserLoginDTO userLoginDTO) {
        UserEntity userEntity;
        if (!userLoginDTO.getEmail().isEmpty()) {
            userEntity = userCredentialsRepository.findByEmail(userLoginDTO.getEmail()).orElseThrow(UserNotExistException::new);
        } else {
            userEntity = userCredentialsRepository.findByNickname(userLoginDTO.getNickname()).orElseThrow(UserNotExistException::new);
        }

        checkBlocked(userEntity);

        String token;

        if (!userEntity.getPassword().equals(userLoginDTO.getPassword().hashCode())) {
            throw new PasswordMismatchException();
        } else {
            token = createToken(userEntity);
        }

        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
        userLoginResponseDTO.setId(userEntity.getId());
        userLoginResponseDTO.setToken(token);

        return userLoginResponseDTO;
    }

    public String createToken(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO(userEntity.getId(), userEntity.getNickname(), userEntity.getEmail());
        return jwtTokenUtil.generateToken(userDTO);
    }


    public void blockAccount(PasswordDTO passwordDTO, String token) {
        UserDTO userDTO = jwtTokenUtil.retrieveClaims(token);
        UserEntity userEntity = userCredentialsRepository.findByEmail(userDTO.getEmail()).orElseThrow(UserNotExistException::new);
        if (!userEntity.getPassword().equals(passwordDTO.getPassword().hashCode())) {
            throw new PasswordMismatchException();
        }

        checkBlocked(userEntity);

        userEntity.setAccountStatus(false);
        userCredentialsRepository.save(userEntity);
    }

    private void checkBlocked(UserEntity userEntity) {
        if (!userEntity.getAccountStatus()) {
            throw new AccountBlockedException(userEntity.getId());
        }
    }
}

package ru.vsu.cs.sheina.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.sheina.authservice.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserCredentialsRepository extends JpaRepository<UserEntity, UUID> {
    Boolean existsByEmail(String email);

    Boolean existsByNickname(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByNickname(String nickname);
}

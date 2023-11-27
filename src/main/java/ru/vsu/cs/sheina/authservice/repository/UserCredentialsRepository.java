package ru.vsu.cs.sheina.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.sheina.authservice.entity.UserEntity;

import java.util.UUID;

public interface UserCredentialsRepository extends JpaRepository<UserEntity, UUID> {
}

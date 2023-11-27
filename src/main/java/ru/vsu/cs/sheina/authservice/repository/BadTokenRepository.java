package ru.vsu.cs.sheina.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.sheina.authservice.entity.BadTokenEntity;

public interface BadTokenRepository extends JpaRepository<BadTokenEntity, String> {
}

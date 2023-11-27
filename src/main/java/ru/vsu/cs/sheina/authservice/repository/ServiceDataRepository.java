package ru.vsu.cs.sheina.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.sheina.authservice.entity.ServiceDataEntity;

import java.util.UUID;

public interface ServiceDataRepository extends JpaRepository<ServiceDataEntity, UUID> {
}

package ru.vsu.cs.sheina.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "service_data")
public class ServiceDataEntity {
    @Id
    @Column(name = "user_id")
    UUID id;

    @Column(name = "key")
    Integer key;
}

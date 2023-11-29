package ru.vsu.cs.sheina.authservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "user_credentials")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(name = "email")
    String email;

    @Column(name = "nickname")
    String nickname;

    @Column(name = "hash_password")
    Integer password;

    @Column(name = "account_status")
    Boolean accountStatus;

    @Column(name = "password_date")
    Timestamp passwordDate;
}

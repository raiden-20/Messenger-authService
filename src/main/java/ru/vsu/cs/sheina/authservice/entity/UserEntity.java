package ru.vsu.cs.sheina.authservice.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

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
    String hashPassword;

    @Column(name = "account_status")
    Boolean accountStatus;
}

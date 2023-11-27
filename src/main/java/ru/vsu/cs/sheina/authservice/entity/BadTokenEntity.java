package ru.vsu.cs.sheina.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "bad_token")
public class BadTokenEntity {
    @Id
    @Column(name = "token")
    String token;
}

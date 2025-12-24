package com.example.instapaws.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private String username;
    private String password;
    private int role = 1;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}

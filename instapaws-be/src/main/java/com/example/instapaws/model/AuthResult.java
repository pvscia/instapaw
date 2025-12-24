package com.example.instapaws.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResult {
    private boolean success;
    private String message;
    private User user;
}

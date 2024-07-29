package com.example.jwtrolebased.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String email;
    private String role;
    private String message;
}

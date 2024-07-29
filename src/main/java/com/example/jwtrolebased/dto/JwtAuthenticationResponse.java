package com.example.jwtrolebased.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String token;

    private String refreshToken;


    private Integer userId; // Added userId field
    private String role; // Added role field
}

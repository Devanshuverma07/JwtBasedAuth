package com.example.jwtrolebased.dto;

import com.example.jwtrolebased.entities.Role;
import lombok.Data;

@Data
public class SignUpRequest {

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private Role role;
}

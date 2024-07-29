package com.example.jwtrolebased.service;

import com.example.jwtrolebased.dto.JwtAuthenticationResponse;
import com.example.jwtrolebased.dto.RefreshTokenRequest;
import com.example.jwtrolebased.dto.SignUpRequest;
import com.example.jwtrolebased.dto.SigninRequest;
import com.example.jwtrolebased.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}

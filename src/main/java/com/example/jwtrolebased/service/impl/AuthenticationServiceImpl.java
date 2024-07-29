package com.example.jwtrolebased.service.impl;

import com.example.jwtrolebased.dto.JwtAuthenticationResponse;
import com.example.jwtrolebased.dto.RefreshTokenRequest;
import com.example.jwtrolebased.dto.SignUpRequest;
import com.example.jwtrolebased.dto.SigninRequest;
import com.example.jwtrolebased.entities.Role;
import com.example.jwtrolebased.entities.User;
import com.example.jwtrolebased.repository.UserRepository;
import com.example.jwtrolebased.service.AuthenticationService;
import com.example.jwtrolebased.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl  implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public User signup(SignUpRequest signUpRequest) {
        User user = new User();

        user.setEmail(signUpRequest.getEmail());
//        System.out.println(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstname());
//        System.out.println(signUpRequest.getFirstname());
        user.setSecondname(signUpRequest.getLastname());

//        System.out.println(signUpRequest.getLastname());


//        user.setRole(Role.USER);

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(signUpRequest.getRole() != null ? signUpRequest.getRole() : Role.USER);

        //System.out.println(user);

        return userRepository.save(user);
    }

    public JwtAuthenticationResponse signin(SigninRequest signinRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signinRequest.getEmail(),
                signinRequest.getPassword())
        );

        var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);

        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setUserId(user.getId()); // Set user ID
        jwtAuthenticationResponse.setRole(user.getRole().name()); // Set user role

        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);


            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            jwtAuthenticationResponse.setUserId(user.getId()); // Set user ID
            jwtAuthenticationResponse.setRole(user.getRole().name()); // Set user role

            return jwtAuthenticationResponse;
        }
        return null;
    }
}

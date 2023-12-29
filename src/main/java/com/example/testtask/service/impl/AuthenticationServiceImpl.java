package com.example.testtask.service.impl;

import com.example.testtask.dao.request.SignUpRequest;
import com.example.testtask.dao.response.JwtAuthenticationResponse;
import com.example.testtask.entity.User;
import com.example.testtask.repository.UserRepository;
import com.example.testtask.service.AuthenticationService;
import com.example.testtask.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public JwtAuthenticationResponse authenticate(SignUpRequest request) {
        var user = User.builder().username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public User saveUser(User request) {
        User newUser = User.builder().username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return userRepository.save(newUser);
    }
}


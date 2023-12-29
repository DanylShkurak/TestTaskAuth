package com.example.testtask.service;

import com.example.testtask.dao.request.SignUpRequest;
import com.example.testtask.dao.response.JwtAuthenticationResponse;
import com.example.testtask.entity.User;

public interface AuthenticationService {
    JwtAuthenticationResponse authenticate(SignUpRequest request);
    User saveUser(User user);
}

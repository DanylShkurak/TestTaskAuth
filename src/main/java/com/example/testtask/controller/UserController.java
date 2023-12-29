package com.example.testtask.controller;

import com.example.testtask.dao.request.SignUpRequest;
import com.example.testtask.dao.response.JwtAuthenticationResponse;
import com.example.testtask.entity.User;
import com.example.testtask.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthenticationResponse> authenticate(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User userRequest) {
        try {
            authenticationService.saveUser(userRequest);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding the user.");
        }
        return ResponseEntity.ok("User added successfully");
    }
}

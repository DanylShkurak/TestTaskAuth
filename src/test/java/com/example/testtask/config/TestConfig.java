package com.example.testtask.config;

import com.example.testtask.service.JwtService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class TestConfig {
    @Bean
    public JwtService jwtService() {
        JwtService jwtServiceMock = Mockito.mock(JwtService.class);

        Mockito.when(jwtServiceMock.extractUserName(Mockito.anyString()))
                .thenReturn("testUser");

        Mockito.when(jwtServiceMock.isTokenValid(Mockito.anyString(), Mockito.any()))
                .thenReturn(true);

        Mockito.when(jwtServiceMock.generateToken(Mockito.any(UserDetails.class)))
                .thenReturn("validToken");

        return jwtServiceMock;
    }


}


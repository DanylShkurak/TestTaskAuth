package com.example.testtask;

import com.example.testtask.dao.request.ProductRequest;
import com.example.testtask.dao.request.SignUpRequest;
import com.example.testtask.entity.User;
import com.example.testtask.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TestTaskApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Test
    public void testAuthenticate() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("x1username", "password");
        String jsonRequest = objectMapper.writeValueAsString(signUpRequest);

        mockMvc.perform(post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void testAddUser() throws Exception {
        User user = new User("xusername", "password");
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("User added successfully"));
    }

    @Test
    public void testAddProducts() throws Exception {
        String validToken = "bearerTokenHere";
        Mockito.when(jwtService.extractUserName(Mockito.anyString()))
                .thenReturn("john12.doe@e12amplwdqe.com");
        Mockito.when(jwtService.isTokenValid(Mockito.anyString(), Mockito.any()))
                .thenReturn(true);
        ProductRequest productRequest = ProductRequest.builder()
                .table("products")
                .records(Arrays.asList(
                        createRecord(
                                "entryDate", "03-01-2023",
                                "itemCode", "11111",
                                "itemName", "Test Inventory 1",
                                "itemQuantity", "20",
                                "status", "Paid"
                        ),
                        createRecord(
                                "entryDate", "03-01-2023",
                                "itemCode", "11111",
                                "itemName", "Test Inventory 2",
                                "itemQuantity", "20",
                                "status", "Paid"
                        )
                ))
                .build();
        String jsonRequest = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products/add")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("Products added successfully"));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        String bearerToken = "bearerTokenHere";
        Mockito.when(jwtService.extractUserName(Mockito.anyString()))
                .thenReturn("john12.doe@e12amplwdqe.com");
        Mockito.when(jwtService.isTokenValid(Mockito.anyString(), Mockito.any()))
                .thenReturn(true);

        mockMvc.perform(get("/products/all")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    private Map<String, String> createRecord(String... values) {
        if (values.length % 2 != 0) {
            throw new IllegalArgumentException("Input must have an even number of elements");
        }

        Map<String, String> record = new HashMap<>();

        for (int i = 0; i < values.length; i += 2) {
            record.put(values[i], values[i + 1]);
        }

        return record;
    }

}

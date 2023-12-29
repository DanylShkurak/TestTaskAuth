package com.example.testtask.controller;

import com.example.testtask.dao.request.ProductRequest;
import com.example.testtask.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProducts(@RequestBody ProductRequest productRequest) {
        try {
            productService.processProductRequest(productRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the product request.");
        }
        return ResponseEntity.ok("Products added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllProducts() {
        List<Map<String, Object>> products = productService.getAllRecords();
        return ResponseEntity.ok(products);
    }

}
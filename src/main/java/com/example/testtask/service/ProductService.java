package com.example.testtask.service;

import com.example.testtask.dao.request.ProductRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {
    void processProductRequest(ProductRequest productRequest);

    void createTable(String tableName, Set<String> columns);

    void saveRecords(String tableName, List<Map<String, String>> records);

    List<Map<String, Object>> getAllRecords();
}

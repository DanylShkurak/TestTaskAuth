package com.example.testtask.service.impl;

import com.example.testtask.dao.mapper.ResultSetMappingUtil;
import com.example.testtask.dao.request.ProductRequest;
import com.example.testtask.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    @PersistenceContext
    private EntityManager entityManager;

    private final String PRODUCTS_TABLE_NAME = "products";

    @Transactional
    public void processProductRequest(ProductRequest productRequest) {
        String tableName = productRequest.getTable();
        List<Map<String, String>> records = productRequest.getRecords();

        createTable(tableName, records.get(0).keySet());

        saveRecords(tableName, records);
    }

    public void createTable(String tableName, Set<String> columns) {
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (id INT AUTO_INCREMENT PRIMARY KEY, ");

        for (String column : columns) {
            query.append(column).append(" VARCHAR(255), ");
        }

        query.delete(query.length() - 2, query.length());
        query.append(")");

        entityManager.createNativeQuery(query.toString()).executeUpdate();
    }

    public void saveRecords(String tableName, List<Map<String, String>> records) {
        for (Map<String, String> record : records) {
            StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
            StringBuilder values = new StringBuilder("VALUES (");

            for (Map.Entry<String, String> entry : record.entrySet()) {
                query.append(entry.getKey()).append(", ");
                values.append("'").append(entry.getValue()).append("', ");
            }

            query.delete(query.length() - 2, query.length());
            values.delete(values.length() - 2, values.length());

            query.append(") ").append(values).append(")");

            entityManager.createNativeQuery(query.toString()).executeUpdate();
        }
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllRecords() {
        String query = "SELECT * FROM " + PRODUCTS_TABLE_NAME;
        List<Object[]> records = entityManager.createNativeQuery(query).getResultList();
        List<Map<String, Object>> mappedRecords = new ArrayList<>();
        for (Object[] record : records) {
            mappedRecords.add(ResultSetMappingUtil.ResultMapping.mapResult(record));
        }
        return mappedRecords;
    }
}



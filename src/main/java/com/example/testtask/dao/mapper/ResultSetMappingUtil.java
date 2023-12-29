package com.example.testtask.dao.mapper;

import java.util.HashMap;
import java.util.Map;

public class ResultSetMappingUtil {
    public static class ResultMapping {
        public ResultMapping() {
        }

        public static Map<String, Object> mapResult(Object[] record) {
            Map<String, Object> mappedResult = new HashMap<>();
            mappedResult.put("entryDate", record[0]);
            mappedResult.put("itemCode", record[1]);
            mappedResult.put("itemName", record[2]);
            mappedResult.put("itemQuantity", record[3]);
            mappedResult.put("status", record[4]);
            return mappedResult;
        }
    }
}

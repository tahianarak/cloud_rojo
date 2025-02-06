package com.crypto.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseFormatter {

    public ResponseFormatter() {} 

    public String formatResponseToJson(Object data, String error) {
        Map<String, Object> response = new HashMap<>();
        
        // Ajout des données à la réponse
        response.put("status", (error != null && !error.isEmpty()) ? "error" : "success");
        response.put("data", data);
        response.put("error", error);
        
        // Conversion de la réponse en JSON
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            e.printStackTrace();
            return "{ \"status\": \"error\", \"message\": \"Error generating JSON response\" }";
        }
    }
}

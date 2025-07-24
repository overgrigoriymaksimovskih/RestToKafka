package com.grigoriy.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grigoriy.dto.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageJsonConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String convertMessageToJsonString(Message message) throws IllegalArgumentException {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            throw new RuntimeException("Error converting message to JSON", e);
        }
    }
}

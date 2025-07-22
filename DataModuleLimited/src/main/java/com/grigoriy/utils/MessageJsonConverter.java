package com.grigoriy.utils;

import com.grigoriy.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class MessageJsonConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Message convertJsonToMessage(String jsonString) throws IllegalArgumentException {
        try {
            return objectMapper.readValue(jsonString, Message.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cant cast JSON-string to object Message: " + e.getMessage(), e);
        }
    }
//    public String convertMessageToUserString(Message message) throws IllegalArgumentException {
//        try {
//            return objectMapper.writeValueAsString(message);
//        } catch (Exception e) {
//            throw new RuntimeException("Error converting message to JSON", e);
//        }
//    }

}

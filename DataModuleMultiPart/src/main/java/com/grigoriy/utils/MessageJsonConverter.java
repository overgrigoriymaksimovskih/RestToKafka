package com.grigoriy.utils;

import com.grigoriy.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class MessageJsonConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Message convertJsonToMessage(String jsonString) throws IllegalArgumentException {
        try {
            // objectMapper.readValue для десериализации JSON в объект Message.
            return objectMapper.readValue(jsonString, Message.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Не удалось преобразовать JSON-строку в объект Message: " + e.getMessage(), e);
        }
    }
//    public String convertJsonToUserString(String jsonString) throws IllegalArgumentException {
//        try {
//            String userListJson = objectMapper.writeValueAsString(jsonString);
//            return userListJson;
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Не удалось преобразовать JSON-строку в String пользователей: " + e.getMessage(), e);
//        }
//    }
}

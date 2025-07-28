package com.grigoriy.repository.impl;

import com.grigoriy.dao.MessageDAO;
import com.grigoriy.entity.Message;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import com.grigoriy.repository.MessageRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class MessageRepositoryImpl implements MessageRepository {
    private final MessageDAO messageDAO;
    private final RedisTemplate<String, String> redisTemplate;
    private final String MESSAGE_KEY_PREFIX = "message:"; // Префикс для ключей Redis

    private final long MESSAGE_TTL_SECONDS = 30; // Время жизни сообщения в Redis (в секундах)


    public MessageRepositoryImpl(MessageDAO messageDAO, RedisTemplate<String, String> redisTemplate) {
        this.messageDAO = messageDAO;
        this.redisTemplate = redisTemplate;
    }


    @Transactional
    @Override
    public String getMessageFromDB(String id) {


        StringBuilder res = new StringBuilder();
        try {
            Instant start = Instant.now(); // Записываем время начала выполнения
            Message message = messageDAO.getReferenceById(Long.parseLong(id));
            Instant finish = Instant.now();// Записываем время окончания выполнения
            res.append("Text: ").append(message.getText()).append("<br>");
            res.append("Created at: ").append(message.getCreatedAt()).append("<br>");
            res.append("User id: ").append(message.getUserId()).append("<br>");

            long timeElapsed = Duration.between(start, finish).toMillis();// Считаем время выполнения
            res.append("<br>").append("Time taken from DB: ").append(timeElapsed).append(" ms");

            // Сохраняем сообщение в Redis
            saveMessageToRedis(id, message);
            return res.toString();

        } catch (EmptyResultDataAccessException e) {
            System.err.println("Message with id " + id + " not found in database.");
            return "Message with id " + id + " not found.";
        } catch (Exception e) {
            System.err.println("Error during getting message: " + e.getMessage());
            return "Error during getting message: " + e.getMessage();
        }
    }

    @Override
    public String getMessageFromRedis(String id) {
        String redisKey = MESSAGE_KEY_PREFIX + id;

        StringBuilder res = new StringBuilder();

        try {
            // Проверяем, существует ли ключ в Redis
            if (!redisTemplate.hasKey(redisKey)) {
                return "Key is absent in redis"; // Ключ отсутствует, нужно читать из БД
            }
            Instant start = Instant.now(); // Записываем время начала выполнения
            Map<Object, Object> rawMessageData = redisTemplate.opsForHash().entries(redisKey);
            Instant finish = Instant.now();  // Записываем время окончания выполнения
            long timeElapsed = Duration.between(start, finish).toMillis(); // Считаем время выполнения

            Map<String, String> messageData = handleRawType(rawMessageData);
            String text = messageData.get("text");
            String createdAt = messageData.get("createdAt");
            String userId = messageData.get("userId");

            if (text == null || createdAt == null || userId == null) {
                return "Key is absent in redis";
            }

            // Формируем строку ответа
            res.append("Text: ").append(text).append("<br>");
            res.append("Created at: ").append(createdAt).append("<br>");
            res.append("User id: ").append(userId).append("<br>");
            res.append("<br>").append("Time taken from REDIS: ").append(timeElapsed).append(" ms");

            return res.toString();
        } catch (Exception e) {
            System.err.println("Error during getting message from Redis: " + e.getMessage());
            return "Error during getting message from Redis: " + e.getMessage();
        }
    }

    private void saveMessageToRedis(String id, Message message) {
        String redisKey = MESSAGE_KEY_PREFIX + id;
        Map<String, String> messageData = new HashMap<>();
        messageData.put("text", message.getText());
        messageData.put("createdAt", message.getCreatedAt().toString());
        messageData.put("userId", message.getUserId().toString());

        redisTemplate.opsForHash().putAll(redisKey, messageData);
        redisTemplate.expire(redisKey, MESSAGE_TTL_SECONDS, TimeUnit.SECONDS);// Устанавливаем время жизни для ключа
    }
    private Map<String, String> handleRawType(Map<Object, Object> rawMessageData){
        if (rawMessageData != null) {
            Map<String, String> messageData = new HashMap<>();
            for (Map.Entry<Object, Object> entry : rawMessageData.entrySet()) {
                if (entry.getKey() instanceof String && entry.getValue() instanceof String) {
                    messageData.put((String) entry.getKey(), (String) entry.getValue());
                } else {
                    // Обработка случая, когда ключ или значение не являются строками
                    System.err.println("Unexpected key or value type in Redis hash.");
                    return null;
                }
            }
            return messageData;
        } else {
            return null;
        }
    }
}

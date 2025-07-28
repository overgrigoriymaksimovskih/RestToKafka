package com.grigoriy.repository.impl.redis;

import com.grigoriy.repository.RedisMessageProcessor;
import com.grigoriy.utils.MessageJsonConverter;
import com.grigoriy.repository.MessageRepository;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisMessageProcessorImpl implements RedisMessageProcessor {

    private final RedisTemplate<String, String> redisTemplate;
    private final MessageRepository messageRepository;
    private final MessageJsonConverter messageJsonConverter;
    private final String keysListKey = "keys_to_process"; // Ключ для хранения списка ключей в Redis


    public RedisMessageProcessorImpl(RedisTemplate<String, String> redisTemplate,
                                     MessageRepository messageRepository,
                                     MessageJsonConverter messageJsonConverter) {
        this.redisTemplate = redisTemplate;
        this.messageRepository = messageRepository;
        this.messageJsonConverter = messageJsonConverter;
    }
    @Override
    public void processMessage(String message) {
        String redisKey = "user:" + messageJsonConverter.convertJsonToMessage(message).getUserId().toString();
        redisTemplate.opsForList().rightPush(redisKey, message);
    }
    @Override
    @Scheduled(fixedRate = 20000)
    public void refreshKeys() {
        Set<String> keys = redisTemplate.keys("user:*");
        if (keys != null && !keys.isEmpty()) {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            redisTemplate.delete(keysListKey); // Очищаем старый список
            listOps.rightPushAll(keysListKey, keys.toArray(new String[0])); // Добавляем новые ключи
            System.out.println("List of keys in Redis was refreshed. Total keys: " + keys.size());
        } else {
            System.out.println("No keys available for refresh list.");
        }
    }
    @Override
    @Scheduled(fixedRate = 1000)
    public void processNextMessage() {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        String key = listOps.leftPop(keysListKey); // Берем первый ключ из списка Redis

        if (key == null) {
            System.out.println("No keys available for processing.");
            return;
        }

        String message = redisTemplate.opsForList().leftPop(key);

        if (message != null) {
            try {
                messageRepository.saveMessage(messageJsonConverter.convertJsonToMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error process next message: " + e.getMessage());
            }
            System.out.println("Message from KEY eas saved: " + key);
        } else {
            // Если список пуст, удаляем ключ
            redisTemplate.delete(key);
            System.out.println("Key was deleted: " + key);
        }

        // Возвращаем ключ в конец списка, если в нем еще есть сообщения.
        if (redisTemplate.opsForList().size(key) > 0) {
            listOps.rightPush(keysListKey, key);
        }
    }
}

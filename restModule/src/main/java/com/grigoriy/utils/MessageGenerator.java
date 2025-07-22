package com.grigoriy.utils;

import com.grigoriy.dto.Message;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MessageGenerator {
    public List<Message> generateRandomMessages(int count, int userId) {

        List<Message> messages = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String randomText = generateRandomText(random, userId);
            messages.add(new Message(randomText, userId));
        }
        return messages;
    }

    private String generateRandomText(Random random, int iteration) {
        int nameLength = random.nextInt(8) + 2; // Длина имени от 2 до 9 символов
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nameLength; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // Случайная буква от 'a' до 'z'
            sb.append(randomChar);
        }
        return sb.toString();
    }
}

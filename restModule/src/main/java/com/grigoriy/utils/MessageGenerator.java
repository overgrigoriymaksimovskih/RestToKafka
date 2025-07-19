package com.grigoriy.utils;

import com.grigoriy.dto.Message;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MessageGenerator {
    public List<Message> generateRandomMessages(int count, int userId) {

//        у меня есть сервис который обрабатывает и присылает сообщения от пользователей
//        (ид пользователя и сообщение) и второй сервис который принимает сообщения
//        и записывает их в БД все работает через рест контроллер. я хочу сделать это через
//        кафку так чтобы в базу записывалось не больше 1 сообщения в секунду от одного
//        пользователя и не больше 10 сообщений от всех пользователей в секунду

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

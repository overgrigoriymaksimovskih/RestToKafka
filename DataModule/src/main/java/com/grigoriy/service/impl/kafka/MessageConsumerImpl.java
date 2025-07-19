package com.grigoriy.service.impl.kafka;

import com.grigoriy.service.MessageConsumer;
import com.grigoriy.repository.MessageRepository;
import com.grigoriy.utils.MessageJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.grigoriy.entity.Message;
import java.util.List;

@Component
public class MessageConsumerImpl implements MessageConsumer {

    private final MessageRepository messageRepository; // Ваш репозиторий для сохранения пользователей
    private final MessageJsonConverter messageJsonConverter;
    private final String REQUEST_TOPIC = "user-requests";

    @Autowired
    public MessageConsumerImpl(MessageRepository messageRepository, MessageJsonConverter messageJsonConverter) {
        this.messageRepository = messageRepository;
        this.messageJsonConverter = messageJsonConverter;
    }

    // Kafka Listener - получает сообщения из Kafka и сразу их обрабатывает
    @Override
    @KafkaListener(topics = REQUEST_TOPIC, groupId = "user-processing-group")
    public void listen(String message) {
        try {
            processMessage(message); // Обрабатываем каждое входящее сообщение
            System.out.println("Received message from Kafka: " + message);
        } catch (Exception e) {
            System.err.println("Exception in Kafka listener: " + e.getMessage());
            e.printStackTrace();
            System.err.println("Failed message: " + message);
        }
    }

    @Override
    public void processMessage(String message) {
        try {
            System.out.println("Saving message to database.");
            Message messages = messageJsonConverter.convertJsonToMessage(message);
            messageRepository.saveUsers(messages);
        } catch (Exception e) {
            System.err.println("Error saving to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
package com.grigoriy.service.impl.kafka;

import com.grigoriy.service.MessageConsumer;
import com.grigoriy.repository.MessageRepository;
import com.grigoriy.utils.MessageJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.grigoriy.entity.Message;

@Component
public class MessageConsumerImpl implements MessageConsumer {

    private final MessageRepository messageRepository;
    private final MessageJsonConverter messageJsonConverter;

    @Autowired
    public MessageConsumerImpl(MessageRepository messageRepository, MessageJsonConverter messageJsonConverter) {
        this.messageRepository = messageRepository;
        this.messageJsonConverter = messageJsonConverter;
    }

    // Kafka Listener - получает сообщения из Kafka и сразу их обрабатывает
    @Override
    @KafkaListener(topics = "${kafka.topic.multi}", groupId = "${kafka.multi.consumer.group-id}")
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
    public void processMessage(String messageStr) {
        try {
            System.out.println("Saving message to database.");
            Message message = messageJsonConverter.convertJsonToMessage(messageStr);
            messageRepository.saveMessage(message);
        } catch (Exception e) {
            System.err.println("Error saving to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
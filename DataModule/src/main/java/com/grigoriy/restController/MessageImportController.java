package com.grigoriy.restController;

import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.beans.factory.annotation.Value;

import com.grigoriy.repository.MessageRepository;
import com.grigoriy.utils.MessageJsonConverter;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageImportController {

    private final KafkaTemplate<String, String> kafkaTemplate;  // String, String for simplicity
    private final String TOPIC = "user-requests";

    private final MessageJsonConverter messageJsonConverter;
    private final MessageRepository messageRepository;

    public MessageImportController(MessageJsonConverter messageJsonConverter, MessageRepository messageRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.messageJsonConverter = messageJsonConverter;
        this.messageRepository = messageRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${api.datamodule.endpoint.upload}")
    private String uploadEndpoint;
    @PostMapping("${api.datamodule.endpoint.upload}") // POST-запрос на /upload
    public String uploadUsers(@RequestBody String jsonString) { // Получаем JSON-строку из тела запроса
        kafkaTemplate.send(TOPIC, jsonString);
//        System.out.println("sent to topic " + jsonString);
        return "Users submitted for processing";
    }
}
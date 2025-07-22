package com.grigoriy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducer {

    @Value("${kafka.topic}")
    String topicName;


    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMessageProducer( KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message, String userId) {
        System.out.println("Sending message to Kafka: " + message);
        kafkaTemplate.send(topicName, userId, message);
    }
}
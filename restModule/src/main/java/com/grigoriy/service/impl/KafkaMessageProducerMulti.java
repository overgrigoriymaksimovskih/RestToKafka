package com.grigoriy.service.impl;

import com.grigoriy.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducerMulti implements MessageProducer {

    @Value("${kafka.topic.multi}")
    String topicName;


    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMessageProducerMulti ( KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public void sendMessage(String message, String userId) {
        System.out.println("Sending message to Kafka: " + message);
        kafkaTemplate.send(topicName, userId, message);
    }
}
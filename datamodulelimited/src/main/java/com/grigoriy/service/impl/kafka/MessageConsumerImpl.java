package com.grigoriy.service.impl.kafka;

import com.grigoriy.repository.impl.redis.RedisMessageProcessorImpl;
import com.grigoriy.service.MessageConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumerImpl implements MessageConsumer {

    RedisMessageProcessorImpl redisMessageProcessorImpl;

    public MessageConsumerImpl(RedisMessageProcessorImpl redisMessageProcessorImpl) {
        this.redisMessageProcessorImpl = redisMessageProcessorImpl;
    }

    @Override
    @KafkaListener(topics = "${kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        try {
            if(message != null){
                try {
                    redisMessageProcessorImpl.processMessage(message);
                    System.out.println("Received message from Kafka: " + message);
                } catch (Exception e) {
                    System.err.println("Error handling in redis processor: " + e.getMessage());
                    e.printStackTrace();
                }
            }else{
                System.out.println("Received message from Kafka is null");
            }

        } catch (Exception e) {
            System.err.println("Exception in Kafka listener: " + e.getMessage());
            e.printStackTrace();
            System.err.println("Failed message: " + message);
        }
    }
}
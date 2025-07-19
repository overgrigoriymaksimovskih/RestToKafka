package com.grigoriy.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");// Замените на ваш адрес Kafka dв интернете
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic userRequestsTopic() {
        return TopicBuilder.name("user-requests")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic delayedUserRequestsTopic() {
        return TopicBuilder.name("delayed-user-requests")
                .partitions(1)
                .replicas(1)
                .build();
    }
}

package com.grigoriy.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    String bootStrapServer;

    @Value("${kafka.topic}")
    String topicName;
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        return new KafkaAdmin(configs);
    }

//    @Bean
//    public NewTopic userRequestsTopic() {
//        return TopicBuilder.name(topicName)
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
    // слушатель все равно пытается подписаться на топик, указанный в kafka.topic, и если этого топика не существует, Kafka автоматически его создаст.
}

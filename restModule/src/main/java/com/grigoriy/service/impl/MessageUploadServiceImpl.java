package com.grigoriy.service.impl;

import com.grigoriy.dto.Message;
import com.grigoriy.service.MessageUploadService;

import java.util.List;

import com.grigoriy.utils.MessageJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageUploadServiceImpl implements MessageUploadService {


    private final MessageJsonConverter messageJsonConverter;
//    private final String uploadUrl = "http://localhost:8086/upload";
//    private final String uploadUrl = "${spring.rabbitmq.queues.login}";
    private final KafkaMessageProducer kafkaMessageProducer;
    @Autowired
    public MessageUploadServiceImpl(MessageJsonConverter messageJsonConverter, KafkaMessageProducer kafkaMessageProducer) {
        this.messageJsonConverter = messageJsonConverter;
        this.kafkaMessageProducer = kafkaMessageProducer;
    }



    @Override
    public String uploadUsers(List<Message> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder errorStringBuilder = new StringBuilder(); // Используем StringBuilder для ошибок
        int successCount = 0;
        int totalCount = messages.size();

        for (Message message : messages) {
            try {
                kafkaMessageProducer.sendMessage(messageJsonConverter.convertJsonToUserString(message));
                successCount++;
            } catch (IllegalArgumentException e) {

                errorStringBuilder.append("Error on pass message [" + message.getText() + "] to kafka topic");
            }
        }
        stringBuilder.append(successCount);
        stringBuilder.append(" of ");
        stringBuilder.append(totalCount);
        stringBuilder.append(" messages were sent successfully. "); // Исправлена опечатка "succesfull"

        if (errorStringBuilder.length() > 0) {
            stringBuilder.append("Errors: ").append(errorStringBuilder.toString()); // Добавляем все ошибки в конец
        }

        return stringBuilder.toString();

    }
}

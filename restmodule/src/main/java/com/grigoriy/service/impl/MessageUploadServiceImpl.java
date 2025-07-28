package com.grigoriy.service.impl;

import com.grigoriy.dto.Message;
import com.grigoriy.service.MessageUploadService;
import java.util.List;
import com.grigoriy.utils.MessageJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageUploadServiceImpl implements MessageUploadService {


    private final MessageJsonConverter messageJsonConverter;
    private final KafkaMessageProducer kafkaMessageProducer;
    private final KafkaMessageProducerMulti kafkaMessageProducerMulti;
    @Autowired
    public MessageUploadServiceImpl(MessageJsonConverter messageJsonConverter, KafkaMessageProducer kafkaMessageProducer, KafkaMessageProducerMulti kafkaMessageProducerMulti) {
        this.messageJsonConverter = messageJsonConverter;
        this.kafkaMessageProducer = kafkaMessageProducer;
        this.kafkaMessageProducerMulti = kafkaMessageProducerMulti;
    }


    @Override
    public String uploadUsers(List<Message> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder errorStringBuilder = new StringBuilder();
        int successCount = 0;
        int totalCount = messages.size();

        for (Message message : messages) {
            try {
                kafkaMessageProducer.sendMessage(messageJsonConverter.convertMessageToJsonString(message), message.getUserId().toString());
                successCount++;
            } catch (IllegalArgumentException e) {

                errorStringBuilder.append("Error on pass message [" + message.getText() + "] to kafka topic");
            }
        }
        stringBuilder.append(successCount);
        stringBuilder.append(" of ");
        stringBuilder.append(totalCount);
        stringBuilder.append(" messages were sent successfully. ");

        if (errorStringBuilder.length() > 0) {
            stringBuilder.append("Errors: ").append(errorStringBuilder.toString());
        }

        return stringBuilder.toString();

    }

    @Override
    public String uploadUsersMulti(List<Message> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder errorStringBuilder = new StringBuilder();
        int successCount = 0;
        int totalCount = messages.size();

        for (Message message : messages) {
            try {
                kafkaMessageProducerMulti.sendMessage(messageJsonConverter.convertMessageToJsonString(message), message.getUserId().toString());
                successCount++;
            } catch (IllegalArgumentException e) {

                errorStringBuilder.append("Error on pass message [" + message.getText() + "] to kafka topic");
            }
        }
        stringBuilder.append(successCount);
        stringBuilder.append(" of ");
        stringBuilder.append(totalCount);
        stringBuilder.append(" messages were sent successfully. ");

        if (errorStringBuilder.length() > 0) {
            stringBuilder.append("Errors: ").append(errorStringBuilder.toString());
        }

        return stringBuilder.toString();

    }
}

package com.grigoriy.service.impl;

import com.grigoriy.dto.Message;
import com.grigoriy.service.MessageUploadService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MessageUploadServiceImpl implements MessageUploadService {

    private final RestTemplate restTemplate;
    private final String uploadUrl = "http://localhost:8086/upload";
//    private final String uploadUrl = "${spring.rabbitmq.queues.login}";
    @Autowired
    public MessageUploadServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public String uploadUsers(List<Message> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder errorStringBuilder = new StringBuilder(); // Используем StringBuilder для ошибок
        int successCount = 0;
        int totalCount = messages.size();

        for (Message message : messages) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Message> requestEntity = new HttpEntity<>(message, headers);

            try {
                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        uploadUrl,
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

                HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();

                if (statusCode.is2xxSuccessful()) {
                    successCount++;
                } else {
                    String errorMessage = "Error in dataModuleConsumer " + message;
                    errorStringBuilder.append(errorMessage).append("; "); // Добавляем ошибку в StringBuilder
                    System.err.println(errorMessage);
                    //Можно добавить обработку, например, повторную отправку
                }
            } catch (Exception e) {
                String errorMessage = "Error uploading message: " + message + ", Error: " + e.getMessage();
                errorStringBuilder.append(errorMessage).append("; "); // Добавляем ошибку в StringBuilder
                System.err.println(errorMessage);
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

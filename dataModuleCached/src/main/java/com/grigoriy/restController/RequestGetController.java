package com.grigoriy.restController;

import com.grigoriy.repository.MessageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestGetController {
    MessageRepository messageRepository;
    public RequestGetController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("${api.datamodule.endpoint.get}")
    @ResponseBody
    public String sendUsersFromDb(
            @RequestParam(value = "messageId", defaultValue = "1") String userId){

        return messageRepository.getMessageFromDB(userId);
    }

    @GetMapping("${api.datamodule.endpoint.getCached}")
    @ResponseBody
    public String sendUsersFromRedis(
            @RequestParam(value = "messageId", defaultValue = "1") String userId){

        return messageRepository.getMessageFromRedis(userId);
    }
}
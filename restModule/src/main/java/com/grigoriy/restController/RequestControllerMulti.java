package com.grigoriy.restController;

import com.grigoriy.dto.Message;
import com.grigoriy.service.MessageUploadService;
import com.grigoriy.utils.MessageGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class RequestControllerMulti {
    private final MessageGenerator messageGenerator;
    private final MessageUploadService messageUploadService;

    public RequestControllerMulti(MessageGenerator userGenerator, MessageUploadService messageUploadService) {
        this.messageGenerator = userGenerator;
        this.messageUploadService = messageUploadService;
    }

    @GetMapping("${api.restmodule.endpoint.upload.multi}")
    @ResponseBody
    public String sendUsers(
            @RequestParam(value = "count", defaultValue = "10") int count,
            @RequestParam(value = "userId", defaultValue = "1") int userId){
//        return "HAHAHHAAH";

        List<Message> messages = messageGenerator.generateRandomMessages(count, userId);
        String uploadResult = messageUploadService.uploadUsersMulti(messages);
//
        return uploadResult;
    }
}
package com.grigoriy.service;

import com.grigoriy.dto.Message;
import java.util.List;

public interface MessageUploadService {
    String uploadUsers(List<Message> users);
    String uploadUsersMulti(List<Message> users);
}

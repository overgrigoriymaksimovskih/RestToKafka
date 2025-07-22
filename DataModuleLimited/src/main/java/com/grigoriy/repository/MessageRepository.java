package com.grigoriy.repository;

import com.grigoriy.entity.Message;

import java.util.List;

public interface MessageRepository {
    String saveMessage(Message message);
}

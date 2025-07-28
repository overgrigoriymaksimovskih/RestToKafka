package com.grigoriy.repository;

import com.grigoriy.entity.Message;

import java.util.List;

public interface MessageRepository {
    String getMessageFromDB(String id);
    String getMessageFromRedis(String id);
}

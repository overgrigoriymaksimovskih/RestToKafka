package com.grigoriy.repository;

public interface RedisMessageProcessor{
    void processMessage(String message);
    void refreshKeys();
    void processNextMessage();
}

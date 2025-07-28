package com.grigoriy.service;

public interface MessageProducer {
    void sendMessage(String message, String userId);
}

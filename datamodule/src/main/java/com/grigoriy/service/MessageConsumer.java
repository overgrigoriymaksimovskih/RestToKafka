package com.grigoriy.service;

public interface MessageConsumer {
    void listen(String message);
    void processMessage(String message);
}

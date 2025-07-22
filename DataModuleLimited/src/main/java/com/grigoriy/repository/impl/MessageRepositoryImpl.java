package com.grigoriy.repository.impl;

import com.grigoriy.dao.MessageDAO;
import com.grigoriy.entity.Message;
import com.grigoriy.repository.MessageRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageRepositoryImpl implements MessageRepository {

    private final MessageDAO messageDAO;

    public MessageRepositoryImpl(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Transactional
    @Override
    public String saveMessage(Message message) {
        try {
            messageDAO.save(message);
            return "Message saved successful";
        } catch (Exception e) {
            System.err.println("Error with saving to DB (saveMessage): " + e.getMessage());
            return "Error with saving to DB (saveMessage): " + e.getMessage();
        }
    }
}
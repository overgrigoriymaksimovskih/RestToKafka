package com.grigoriy.repository.impl;

import com.grigoriy.dao.MessageDAO;
import com.grigoriy.entity.Message;
import com.grigoriy.repository.MessageRepository;
import com.grigoriy.utils.MessageJsonConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MessageRepositoryImpl implements MessageRepository {
    private final MessageDAO messageDAO;

    public MessageRepositoryImpl(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }


    @Transactional
    @Override
    public String saveUsers(Message message) {
        try {
            messageDAO.save(message);
            return "Сообщение успешно сохранено";
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении сообщения: " + e.getMessage());
            return "Ошибка при сохранении сообщения: " + e.getMessage();
        }
    }

//    @PostConstruct
//    private void createuser(){
//        List<User> users = new ArrayList<>();
//        users.add(new User("firstUserFromArray"));
//
//
//        System.out.println(saveUsers(users));
//    }

}

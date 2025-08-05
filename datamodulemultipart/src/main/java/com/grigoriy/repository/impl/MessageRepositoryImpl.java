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
            //Тут, конечно, бестпрактис надо вернуть ид сохраненной сущности...
            return "Message was saved succcessful";
        } catch (Exception e) {
            //Тут, конечно, 100% надо записать ошибку в лог и выкинуть ее, чтобы транзакция откатилась...
            System.err.println("Error during saving message: " + e.getMessage());
            return "Error during saving message: " + e.getMessage();
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

//package com.grigoriy.repository.impl.redis;
//
//import com.grigoriy.repository.MessageRequestRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//@Repository
//public class MessageRequestRepositoryImpl implements MessageRequestRepository {
//    private static final String LIST_KEY = "user_requests";
//
//    private RedisTemplate<String, String> redisTemplate;
//    private ListOperations<String, String> listOperations;
//
//    public MessageRequestRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @PostConstruct
//    private void init() {
//        listOperations = redisTemplate.opsForList();
//    }
//
//    @Override
//    public void save(String request) {
//        listOperations.rightPush(LIST_KEY, request);
//    }
//
//    @Override
//    public String getFirst() {
//        return listOperations.index(LIST_KEY, 0);
//    }
//
//    @Override
//    public List<String> getAll() {
//        Long size = listOperations.size(LIST_KEY);
//        if (size != null) {
//            return listOperations.range(LIST_KEY, 0, size - 1);
//        } else {
//            return List.of();
//        }
//    }
//
//    @Override
//    public Long size() {
//        return listOperations.size(LIST_KEY);
//    }
//
//    @Override
//    public String popFirst() {
//        return listOperations.leftPop(LIST_KEY);
//    }
//}

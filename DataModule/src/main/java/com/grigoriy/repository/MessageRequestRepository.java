package com.grigoriy.repository;

import java.util.List;

public interface MessageRequestRepository {
    void save(String request);
    String getFirst(); // Changed from User to String
    List<String> getAll(); // Changed from User to String
    Long size();
    String popFirst();
}

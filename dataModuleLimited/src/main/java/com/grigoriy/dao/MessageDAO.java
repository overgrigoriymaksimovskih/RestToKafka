package com.grigoriy.dao;

import com.grigoriy.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDAO extends JpaRepository<Message, Long> {
}

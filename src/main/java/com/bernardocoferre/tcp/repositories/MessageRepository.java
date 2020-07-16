package com.bernardocoferre.tcp.repositories;

import com.bernardocoferre.tcp.model.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllByOrderByIdDesc();

}

package com.bernardocoferre.tcp.repositories;

import com.bernardocoferre.tcp.model.MessageReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageReferenceRepository extends JpaRepository<MessageReference, Integer> {

}

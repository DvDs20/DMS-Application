package com.dms.dmsapplication.messages.repository;

import com.dms.dmsapplication.messages.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessagesRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByStudentId(Long studentId);

}

package com.dms.dmsapplication.messages.repository;

import com.dms.dmsapplication.messages.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Message, Long> {

}

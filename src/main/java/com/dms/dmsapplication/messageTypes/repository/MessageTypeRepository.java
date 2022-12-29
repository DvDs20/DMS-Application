package com.dms.dmsapplication.messageTypes.repository;

import com.dms.dmsapplication.messageTypes.models.MessageTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTypeRepository extends JpaRepository<MessageTypes, Long> {

}

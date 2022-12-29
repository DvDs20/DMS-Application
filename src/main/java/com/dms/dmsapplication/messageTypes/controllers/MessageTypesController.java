package com.dms.dmsapplication.messageTypes.controllers;

import com.dms.dmsapplication.messageTypes.models.MessageTypes;
import com.dms.dmsapplication.messageTypes.repository.MessageTypeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class MessageTypesController {

    private final MessageTypeRepository messageTypeRepository;

    public MessageTypesController(MessageTypeRepository messageTypeRepository) {
        this.messageTypeRepository = messageTypeRepository;
    }

    @GetMapping("/messages-types")
    @PreAuthorize("hasRole('STUDENT')")
    public List<MessageTypes> getMessagesTypes() {
        return messageTypeRepository.findAll();
    }
}

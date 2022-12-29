package com.dms.dmsapplication.messages.controllers;

import com.dms.dmsapplication.exception.ResourceNotFoundException;
import com.dms.dmsapplication.messages.payload.request.NewMessageRequest;
import com.dms.dmsapplication.messages.payload.response.MessagesResponse;
import com.dms.dmsapplication.messages.services.MessagesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class MessagesController {

    private final MessagesService messagesService;

    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("/send-new-message")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> sendNewMessage(@RequestBody NewMessageRequest request) throws ResourceNotFoundException {
        messagesService.sendNewMessage(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages")
    @PreAuthorize("hasRole('ADMIN')")
    public List<MessagesResponse> getAllMessages() throws ResourceNotFoundException {
        return messagesService.getAllMessages();
    }

    @DeleteMapping("/messages/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMessage(@PathVariable(value = "id") long messageId) throws ResourceNotFoundException {
        messagesService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }

}

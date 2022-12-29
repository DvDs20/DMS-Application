package com.dms.dmsapplication.messages.services;

import com.dms.dmsapplication.entity.UserRooms;
import com.dms.dmsapplication.exception.ResourceNotFoundException;
import com.dms.dmsapplication.messages.models.Message;
import com.dms.dmsapplication.messages.payload.request.NewMessageRequest;
import com.dms.dmsapplication.messages.payload.response.MessagesResponse;
import com.dms.dmsapplication.messages.repository.MessagesRepository;
import com.dms.dmsapplication.models.User;
import com.dms.dmsapplication.repository.UserRepository;
import com.dms.dmsapplication.repository.UserRoomRepository;
import com.dms.dmsapplication.rooms.models.Room;
import com.dms.dmsapplication.rooms.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;

    private final UserRepository userRepository;

    private final UserRoomRepository userRoomRepository;

    private final RoomRepository roomRepository;

    public MessagesService(MessagesRepository messagesRepository, UserRepository userRepository,
            UserRoomRepository userRoomRepository, RoomRepository roomRepository) {
        this.messagesRepository = messagesRepository;
        this.userRepository = userRepository;
        this.userRoomRepository = userRoomRepository;
        this.roomRepository = roomRepository;
    }

    public void sendNewMessage(NewMessageRequest request) {
        Message message = new Message();
        Date sendDate = Date.valueOf(getCurrentDate());
        message.setStudentId(request.getStudentId());
        message.setMessageType(request.getMessageType());
        message.setMessageTitle(request.getMessageTitle());
        message.setMessage(request.getMessage());
        message.setMessageDate(sendDate);

        messagesRepository.save(message);
    }

    public List<MessagesResponse> getAllMessages() {
        List<Message> messages = messagesRepository.findAll();
        List<MessagesResponse> response = new ArrayList<>();
        for (Message message : messages) {
            MessagesResponse messagesResponse = new MessagesResponse();
            User user;
            user = userRepository.findById(message.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not fount with id: " + message.getStudentId()));
            UserRooms userRooms;
            userRooms = userRoomRepository.findUserRoomsByUserId(message.getStudentId());
            Room room;
            room = roomRepository.findById(userRooms.getRoomId())
                                         .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + userRooms.getRoomId()));


            messagesResponse.setId(message.getId());
            messagesResponse.setFirstName(user.getFirstName());
            messagesResponse.setLastName(user.getLastName());
            messagesResponse.setRoomNumber(room.getRoomNumber());
            messagesResponse.setMessageDate(message.getMessageDate());
            messagesResponse.setMessageType(message.getMessageType());
            messagesResponse.setMessageTitle(message.getMessageTitle());
            messagesResponse.setMessage(message.getMessage());
            response.add(messagesResponse);
        }
        return response;
    }

    public void deleteMessage(Long messageId) {
        messagesRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + messageId));
        messagesRepository.deleteById(messageId);
    }

    public String getCurrentDate() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

}

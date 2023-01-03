package com.dms.dmsapplication.service;

import com.dms.dmsapplication.entity.UserRooms;
import com.dms.dmsapplication.exception.ResourceNotFoundException;
import com.dms.dmsapplication.messages.models.Message;
import com.dms.dmsapplication.messages.repository.MessagesRepository;
import com.dms.dmsapplication.parcels.model.Parcel;
import com.dms.dmsapplication.parcels.repository.ParcelRepository;
import com.dms.dmsapplication.repository.UserRepository;
import com.dms.dmsapplication.repository.UserRoomRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserRoomRepository userRoomRepository;

    private final ParcelRepository parcelRepository;

    private final MessagesRepository messagesRepository;

    public UserService(UserRepository userRepository, UserRoomRepository userRoomRepository,
            ParcelRepository parcelRepository, MessagesRepository messagesRepository) {
        this.userRepository = userRepository;
        this.userRoomRepository = userRoomRepository;
        this.parcelRepository = parcelRepository;
        this.messagesRepository = messagesRepository;
    }

    public void deleteStudent(Long userId) {
//        userRoomRepository.deleteByUserId(userId);
        if (parcelRepository.findByStudentId(userId).isPresent()) {
            Parcel parcel = parcelRepository.findByStudentId(userId)
                                                    .orElseThrow(() -> new ResourceNotFoundException("Parcel not "
                                                            + "found with studentId: " + userId));
            parcelRepository.deleteById(parcel.getId());
        }
        if (userRoomRepository.findByUserId(userId).isPresent()) {
            UserRooms userRooms = userRoomRepository.findUserRoomsByUserId(userId);
            userRoomRepository.delete(userRooms);
        }
        if (messagesRepository.findByStudentId(userId).isPresent()) {
            Message message = messagesRepository.findByStudentId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Message not found with studentId: " + userId));

            messagesRepository.delete(message);
        }
        userRepository.deleteById(userId);

    }

}

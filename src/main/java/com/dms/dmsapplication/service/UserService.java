package com.dms.dmsapplication.service;

import com.dms.dmsapplication.repository.UserRepository;
import com.dms.dmsapplication.repository.UserRoomRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserRoomRepository userRoomRepository;

    public UserService(UserRepository userRepository, UserRoomRepository userRoomRepository) {
        this.userRepository = userRepository;
        this.userRoomRepository = userRoomRepository;
    }

    public void deleteStudent(Long userId) {
//        userRoomRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

}

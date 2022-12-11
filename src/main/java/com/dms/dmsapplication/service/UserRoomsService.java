package com.dms.dmsapplication.service;

import com.dms.dmsapplication.entity.UserRooms;
import com.dms.dmsapplication.models.User;
import com.dms.dmsapplication.repository.UserRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class UserRoomsService {

    private final UserRoomRepository userRoomRepository;

    public UserRoomsService(UserRoomRepository userRoomRepository) {
        this.userRoomRepository = userRoomRepository;
    }

    public Optional<Long> getRoomIdByUserId(Long userId) {
        List<UserRooms> userRoomsList = userRoomRepository.findAll();

        Predicate<UserRooms> byUserId = userRooms -> Objects.equals(userRooms.getUserId(), userId);

        return userRoomsList.stream().filter(byUserId)
                            .map(UserRooms::getUserId).findFirst();
    }

    public void saveRoomIdAndUserIds(Long userId, Long roomId) {
        UserRooms userRooms = new UserRooms();

        userRooms.setUserId(userId);
        userRooms.setRoomId(roomId);

        userRoomRepository.save(userRooms);
    }



}

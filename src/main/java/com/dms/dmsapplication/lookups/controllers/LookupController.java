package com.dms.dmsapplication.lookups.controllers;

import com.dms.dmsapplication.lookups.payload.response.ResponseForRoomsLookup;
import com.dms.dmsapplication.lookups.payload.response.ResponseForStudentsLookup;
import com.dms.dmsapplication.models.User;
import com.dms.dmsapplication.rooms.models.Room;
import com.dms.dmsapplication.rooms.repository.RoomRepository;
import com.dms.dmsapplication.security.services.UserDetailsServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class LookupController {

    private final UserDetailsServiceImpl userDetailsService;

    private final RoomRepository roomRepository;

    public LookupController(UserDetailsServiceImpl userDetailsService1, RoomRepository roomRepository) {

        this.userDetailsService = userDetailsService1;
        this.roomRepository = roomRepository;
    }

    @GetMapping("/students-without-expired-contracts")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ResponseForStudentsLookup> getAllStudentsWithoutContract() {
        Predicate<User> ifStudentStatusIsExpiredContract = user -> Objects.equals(user.getUserStatus(), 0);
        Predicate<User> ifStudentStatusIsWithoutContract = user -> Objects.equals(user.getUserStatus(), 2);
        List<User> usersWithExpiredContract =
                userDetailsService.getAllStudents().stream().filter(ifStudentStatusIsExpiredContract).toList();
        List<User> usersWithoutContracts =
                userDetailsService.getAllStudents().stream().filter(ifStudentStatusIsWithoutContract).toList();

        List<User> fullList = Stream.concat(usersWithExpiredContract.stream(),
                usersWithoutContracts.stream()).toList();

        List<ResponseForStudentsLookup> studentsLookupList = new ArrayList<>();
        for (User user : fullList) {
            ResponseForStudentsLookup responseForStudentsLookup = new ResponseForStudentsLookup();
            responseForStudentsLookup.setStudentId(user.getId());
            responseForStudentsLookup.setFirstName(user.getFirstName());
            responseForStudentsLookup.setLastName(user.getLastName());
            studentsLookupList.add(responseForStudentsLookup);
        }

        return studentsLookupList;
    }

    @GetMapping("/free-rooms")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ResponseForRoomsLookup> getAllFreeRooms() {
        Predicate<Room> ifRoomIsFree = room -> Objects.equals(room.getRoomStatus(), 1);
        Predicate<Room> ifRoomIsPartiallyFree = room -> Objects.equals(room.getRoomStatus(), 2);

        List<Room> roomsWhichAreFullyFree =
                roomRepository.findAll().stream().filter(ifRoomIsFree).toList();
        List<Room> roomsWhichArePartiallyFree =
                roomRepository.findAll().stream().filter(ifRoomIsPartiallyFree).toList();

        List<Room> fullList =
                Stream.concat(roomsWhichArePartiallyFree.stream(), roomsWhichAreFullyFree.stream()).toList();

        List<ResponseForRoomsLookup> roomsLookupList = new ArrayList<>();
        for (Room room : fullList) {
            ResponseForRoomsLookup responseForRoomsLookup = new ResponseForRoomsLookup();
            responseForRoomsLookup.setRoomId(room.getId());
            responseForRoomsLookup.setRoomNumber(room.getRoomNumber());
            roomsLookupList.add(responseForRoomsLookup);
        }

        return roomsLookupList;
    }

}

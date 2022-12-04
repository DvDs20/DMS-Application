package com.dms.dmsapplication.rooms.controller;

import com.dms.dmsapplication.exception.ResourceNotFoundException;
import com.dms.dmsapplication.rooms.models.Room;
import com.dms.dmsapplication.rooms.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    // get all rooms
    @GetMapping("/rooms")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // add room
    @PostMapping("/rooms")
    @PreAuthorize("hasRole('ADMIN')")
    public Room addRoom(@RequestBody Room room) {
        return roomRepository.save(room);
    }

    @DeleteMapping("rooms/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRoom(@PathVariable(value = "id") long roomId) throws ResourceNotFoundException {
        roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with " + roomId));
        roomRepository.deleteById(roomId);
        return ResponseEntity.ok().build();
    }
}

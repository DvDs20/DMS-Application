package com.dms.dmsapplication.controllers;

import com.dms.dmsapplication.exception.ResourceNotFoundException;
import com.dms.dmsapplication.models.User;
import com.dms.dmsapplication.repository.UserRepository;
import com.dms.dmsapplication.repository.UserRoomRepository;
import com.dms.dmsapplication.security.services.UserDetailsImpl;
import com.dms.dmsapplication.security.services.UserDetailsServiceImpl;
import com.dms.dmsapplication.service.UserRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @Autowired
    PasswordEncoder encoder;

    private final UserRoomsService userRoomsService;

    private final UserRoomRepository userRoomRepository;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserRepository userRepository;

    public ApiController(UserRoomsService userRoomsService, UserRoomRepository userRoomRepository,
            UserDetailsServiceImpl userDetailsService,
            UserRepository userRepository) {
        this.userRoomsService = userRoomsService;
        this.userRoomRepository = userRoomRepository;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public String userAccess() {
        return "Student Board.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/test/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getTest(@PathVariable(value = "id") long userId) {
        return userRoomsService.getRoomIdByUserId(userId).toString();
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllStudents() {
        return userDetailsService.getAllStudents();
    }

    @GetMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserById(@PathVariable(value = "id") long userId) {

        return userDetailsService.getUserDetailsByUserId(userId);
    }

    @PutMapping("students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateStudent(@PathVariable(value = "id") long userId,
            @RequestBody UserDetailsImpl userDetails) throws ResourceNotFoundException {
        User user = userDetailsService.getUserDetailsByUserId(userId);
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(encoder.encode(userDetails.getPassword()));
        user.setAcademicGroup(userDetails.getAcademicGroup());
        user.setNumber(userDetails.getNumber());

        userRepository.save(user);

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") long userId) throws ResourceNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (userRoomRepository.findByUserId(userId).isEmpty())
        {
            userRepository.deleteById(userId);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().body("User can't be deleted because of userStatus");
        }
    }
}
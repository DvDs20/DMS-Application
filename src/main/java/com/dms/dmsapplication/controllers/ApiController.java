package com.dms.dmsapplication.controllers;

import com.dms.dmsapplication.models.User;
import com.dms.dmsapplication.security.services.UserDetailsServiceImpl;
import com.dms.dmsapplication.service.UserRoomsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final UserRoomsService userRoomsService;

    private final UserDetailsServiceImpl userDetailsService;

    public ApiController(UserRoomsService userRoomsService, UserDetailsServiceImpl userDetailsService) {
        this.userRoomsService = userRoomsService;
        this.userDetailsService = userDetailsService;
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
}
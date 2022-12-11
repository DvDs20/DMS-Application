package com.dms.dmsapplication.lookups.controllers;

import com.dms.dmsapplication.models.User;
import com.dms.dmsapplication.security.services.UserDetailsServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class LookupController {

    private final UserDetailsServiceImpl userDetailsService;

    public LookupController(UserDetailsServiceImpl userDetailsService1) {

        this.userDetailsService = userDetailsService1;
    }

    @GetMapping("/students-without-expired-contracts")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllStudentsWithoutContract() {
        Predicate<User> ifStudentStatusIsExpiredContract = user -> Objects.equals(user.getUserStatus(), 0);
        Predicate<User> ifStudentStatusIsWithoutContract = user -> Objects.equals(user.getUserStatus(), 2);
        List<User> usersWithExpiredContract =
                userDetailsService.getAllStudents().stream().filter(ifStudentStatusIsExpiredContract).toList();
        List<User> usersWithoutContracts =
                userDetailsService.getAllStudents().stream().filter(ifStudentStatusIsWithoutContract).toList();

        return Stream.concat(usersWithExpiredContract.stream(),
                usersWithoutContracts.stream()).toList();
    }

}

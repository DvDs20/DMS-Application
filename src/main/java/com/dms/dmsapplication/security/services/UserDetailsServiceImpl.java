package com.dms.dmsapplication.security.services;

import com.dms.dmsapplication.contracts.models.Contract;
import com.dms.dmsapplication.contracts.repository.ContractsRepository;
import com.dms.dmsapplication.exception.ResourceNotFoundException;
import com.dms.dmsapplication.models.ERole;
import com.dms.dmsapplication.models.Role;
import com.dms.dmsapplication.models.User;
import com.dms.dmsapplication.payload.response.StudentInfoResponse;
import com.dms.dmsapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ContractsRepository contractsRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public List<User> getAllStudents() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, ERole.ROLE_STUDENT));
        return userRepository.findAllByRolesIsIn(roles);
    }

    public List<StudentInfoResponse> getStudentsList() {
        List<User> studentsList = getAllStudents();
        List<StudentInfoResponse> studentInfoResponse = new ArrayList<>();
        for (User user : studentsList) {
            StudentInfoResponse response = new StudentInfoResponse();
            response.setId(user.getId());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setEmail(user.getEmail());
            response.setNumber(user.getNumber());
            response.setUsername(user.getUsername());
            response.setUserStatus(user.getUserStatus());
            response.setAcademicGroup(user.getAcademicGroup());
            if (contractsRepository.findByStudentId(user.getId()).isPresent()) {
                Contract contract = contractsRepository.findByStudentId(user.getId())
                                                               .orElseThrow(() -> new ResourceNotFoundException(
                                                                       "Contract not found with studentId: " + user.getId()));
                response.setContractId(contract.getId());
            } else {
                response.setContractId(null);
            }
            studentInfoResponse.add(response);
        }
        return studentInfoResponse;
    }

    public User getUserDetailsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with id: " + userId));

        return user;
    }

//    public UserDetailsImpl updateUserDetailsByUserId(Long userId, UserDetailsImpl userDetails) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + userId));
//
//        user.se
//    }

}
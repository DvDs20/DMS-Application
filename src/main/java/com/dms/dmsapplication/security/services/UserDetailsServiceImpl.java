package com.dms.dmsapplication.security.services;

import com.dms.dmsapplication.models.ERole;
import com.dms.dmsapplication.models.Role;
import com.dms.dmsapplication.models.User;
import com.dms.dmsapplication.repository.RoleRepository;
import com.dms.dmsapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

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

}
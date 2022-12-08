package com.dms.dmsapplication.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.dms.dmsapplication.models.Role;
import com.dms.dmsapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findAllByRolesIsIn(Set<Role> roles);

//    List<User> findUsersByRolesContainsIgnoreCaseOrderByIdAsc(Set<Role> roles);
}
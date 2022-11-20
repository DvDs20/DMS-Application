package com.dms.dmsapplication.repository;

import java.util.Optional;

import com.dms.dmsapplication.models.ERole;
import com.dms.dmsapplication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
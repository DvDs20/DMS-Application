package com.dms.dmsapplication.repository;

import com.dms.dmsapplication.models.ERole;
import com.dms.dmsapplication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}

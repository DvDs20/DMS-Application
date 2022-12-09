package com.dms.dmsapplication.repository;

import com.dms.dmsapplication.entity.UserRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRooms, Long> {

    Optional<Long> findByUserId(Long userId);
}

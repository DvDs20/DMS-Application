package com.dms.dmsapplication.repository;

import com.dms.dmsapplication.entity.UserRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRooms, Long> {

}

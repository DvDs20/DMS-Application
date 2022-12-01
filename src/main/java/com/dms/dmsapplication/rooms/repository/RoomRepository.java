package com.dms.dmsapplication.rooms.repository;

import com.dms.dmsapplication.rooms.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}

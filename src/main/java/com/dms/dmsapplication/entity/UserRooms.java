package com.dms.dmsapplication.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user_room")
public class UserRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "public.user_room_id_seq")
    @SequenceGenerator(name = "public.user_room_id_seq",
                       sequenceName = "public.user_room_id_seq", allocationSize = 1)
    @Column(name = "user_room_id", unique = true, nullable = false)
    private Long userRoomId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    public UserRooms(Long userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }

    public UserRooms() {

    }

    public Long getUserRoomId() {
        return userRoomId;
    }

    public void setUserRoomId(Long userRoomId) {
        this.userRoomId = userRoomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

}

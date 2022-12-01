package com.dms.dmsapplication.rooms.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "rooms",
       uniqueConstraints = {
        @UniqueConstraint(columnNames = "room_number")
       }
       )
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "public.rooms_id_seq")
    @SequenceGenerator(name = "public.rooms_id_seq",
                       sequenceName = "public.rooms_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "room_status")
    private Integer roomStatus;

    @NotBlank
    @Size(max = 4)
    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "room_capacity")
    private Integer roomCapacity;


    public Room(Integer roomStatus, String roomNumber, Integer floor, Integer roomCapacity) {
        this.roomStatus = roomStatus;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.roomCapacity = roomCapacity;
    }

    public Room() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Integer roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(Integer roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

}

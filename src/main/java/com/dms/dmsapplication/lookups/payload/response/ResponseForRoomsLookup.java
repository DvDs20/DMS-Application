package com.dms.dmsapplication.lookups.payload.response;

import javax.validation.constraints.NotBlank;

public class ResponseForRoomsLookup {

    @NotBlank
    private Long roomId;

    @NotBlank
    private String roomNumber;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

}

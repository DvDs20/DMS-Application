package com.dms.dmsapplication.contracts.payload.request;

import java.sql.Date;
import javax.validation.constraints.NotBlank;

public class NewContractCreationRequest {

    @NotBlank
    private Long studentId;

    @NotBlank
    private Long roomId;

    @NotBlank
    private Date expireDate;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

}

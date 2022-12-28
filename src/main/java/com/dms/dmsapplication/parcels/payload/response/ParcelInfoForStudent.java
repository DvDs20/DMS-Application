package com.dms.dmsapplication.parcels.payload.response;

import java.sql.Date;
import javax.validation.constraints.NotBlank;

public class ParcelInfoForStudent {

    @NotBlank
    private Long id;

    @NotBlank
    private String messageTitle;

    @NotBlank
    private String message;

    @NotBlank
    private Date messageTime;

    @NotBlank
    private Integer viewStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public Integer getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(Integer viewStatus) {
        this.viewStatus = viewStatus;
    }

}

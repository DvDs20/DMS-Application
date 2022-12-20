package com.dms.dmsapplication.parcels.payload.request;

import javax.validation.constraints.NotBlank;

public class NewParcelMessageCreationRequest {

    @NotBlank
    private Long studentId;

    @NotBlank
    private String message;

    @NotBlank
    private String messageTitle;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

}

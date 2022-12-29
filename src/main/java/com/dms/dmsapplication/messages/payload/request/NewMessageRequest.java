package com.dms.dmsapplication.messages.payload.request;

import javax.validation.constraints.NotBlank;

public class NewMessageRequest {

    @NotBlank
    private Long studentId;

    @NotBlank
    private String messageType;

    @NotBlank
    private String messageTitle;

    @NotBlank
    private String message;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
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

}

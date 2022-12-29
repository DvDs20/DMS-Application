package com.dms.dmsapplication.messages.models;

import com.sun.jdi.PrimitiveValue;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "public.message_id_seq")
    @SequenceGenerator(name = "public.message_id_seq",
                       sequenceName = "public.message_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "message_type", nullable = false)
    private String messageType;

    @Column(name = "message_title", nullable = false)
    private String messageTitle;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "message_date", nullable = false)
    private Date messageDate;

    public Message(Long studentId, String messageType, String messageTitle, String message, Date messageDate) {
        this.studentId = studentId;
        this.messageType = messageType;
        this.messageTitle = messageTitle;
        this.message = message;
        this.messageDate = messageDate;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

}

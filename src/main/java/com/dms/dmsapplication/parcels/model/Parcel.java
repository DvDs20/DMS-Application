package com.dms.dmsapplication.parcels.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "parcels")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "public.parcel_id_seq")
    @SequenceGenerator(name = "public.parcel_id_seq",
                       sequenceName = "public.parcel_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "message_time", nullable = false)
    private Date messageTime;

    @Column(name = "message_title", nullable = false)
    private String messageTitle;

    @Column(name = "view_status", nullable = false)
    private Integer viewStatus;

    public Parcel(Long id, Long studentId, String message, Date messageTime, String messageTitle, Integer viewStatus) {
        this.id = id;
        this.studentId = studentId;
        this.message = message;
        this.messageTime = messageTime;
        this.messageTitle = messageTitle;
        this.viewStatus = viewStatus;
    }

    public Parcel() {

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

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public Integer getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(Integer viewStatus) {
        this.viewStatus = viewStatus;
    }

}

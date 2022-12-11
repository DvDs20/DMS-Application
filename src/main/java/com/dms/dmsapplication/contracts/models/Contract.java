package com.dms.dmsapplication.contracts.models;

import net.bytebuddy.dynamic.loading.InjectionClassLoader.Strategy;
import org.springframework.stereotype.Controller;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contracts",
       uniqueConstraints = {
        @UniqueConstraint(columnNames = "contract_number")
       })
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "public.contract_id_seq")
    @SequenceGenerator(name = "public.contract_id_seq",
                       sequenceName = "public.contract_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "contract_number", nullable = false)
    @Size(max = 15)
    private String contractNumber;

    @Column(name = "expire_date", nullable = false)
    private Date expireDate;

    @Column(nullable = false)
    private Integer status;

    public Contract(Long studentId, Long roomId, String contractNumber, Date expireDate, Integer status) {
        this.studentId = studentId;
        this.roomId = roomId;
        this.contractNumber = contractNumber;
        this.expireDate = expireDate;
        this.status = status;
    }

    public Contract() {

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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

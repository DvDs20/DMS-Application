package com.dms.dmsapplication.contracts.payload.response;

import java.sql.Date;
import javax.validation.constraints.NotBlank;

public class ResponseForContractInfoForUser {

    @NotBlank
    private Long id;

    @NotBlank
    private String roomNumber;

    @NotBlank
    private Date expireDate;

    @NotBlank
    private String contractNumber;

    @NotBlank
    private Integer contractStatus;

    @NotBlank
    private String priceForStudent;

    @NotBlank
    private Integer userStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getPriceForStudent() {
        return priceForStudent;
    }

    public void setPriceForStudent(String priceForStudent) {
        this.priceForStudent = priceForStudent;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

}

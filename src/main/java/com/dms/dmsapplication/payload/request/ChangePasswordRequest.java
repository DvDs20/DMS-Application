package com.dms.dmsapplication.payload.request;

import javax.validation.constraints.NotBlank;

public class ChangePasswordRequest {

    @NotBlank
    private Long id;

    @NotBlank
    private String password;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String newRepeatedPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewRepeatedPassword() {
        return newRepeatedPassword;
    }

    public void setNewRepeatedPassword(String newRepeatedPassword) {
        this.newRepeatedPassword = newRepeatedPassword;
    }

}

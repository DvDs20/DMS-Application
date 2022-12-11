package com.dms.dmsapplication.lookups.payload.response;

import javax.validation.constraints.NotBlank;

public class ResponseForStudentsLookup {

    @NotBlank
    private Long studentId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}

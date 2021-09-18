package com.yoviro.rest.dto.search;

import com.yoviro.rest.config.enums.OfficialIdTypeEnum;

public class SearchContactDTO {
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private OfficialIdTypeEnum officialIDTypeEnum;
    private String officialIDNumber;
    private String email;
    private Boolean exactCoincidence;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public OfficialIdTypeEnum getOfficialIDType() {
        return officialIDTypeEnum;
    }

    public void setOfficialIDType(OfficialIdTypeEnum officialIDTypeEnum) {
        this.officialIDTypeEnum = officialIDTypeEnum;
    }

    public String getOfficialIDNumber() {
        return officialIDNumber;
    }

    public void setOfficialIDNumber(String officialIDNumber) {
        this.officialIDNumber = officialIDNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getExactCoincidence() {
        return exactCoincidence;
    }

    public void setExactCoincidence(Boolean exactCoincidence) {
        this.exactCoincidence = exactCoincidence;
    }
}

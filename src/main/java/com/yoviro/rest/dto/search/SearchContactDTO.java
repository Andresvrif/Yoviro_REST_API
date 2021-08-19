package com.yoviro.rest.dto.search;

import com.yoviro.rest.config.enums.OfficialIdType;

public class SearchContactDTO {
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private OfficialIdType officialIDType;
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

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public OfficialIdType getOfficialIDType() {
        return officialIDType;
    }

    public void setOfficialIDType(OfficialIdType officialIDType) {
        this.officialIDType = officialIDType;
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

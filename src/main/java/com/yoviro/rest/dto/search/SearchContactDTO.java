package com.yoviro.rest.dto.search;

import com.yoviro.rest.config.enums.OfficialIdTypeEnum;

public abstract class SearchContactDTO {
    private String name;
    private String email;
    private OfficialIdTypeEnum officialIDTypeEnum;
    private String officialIDNumber;
    private Boolean exactCoincidence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OfficialIdTypeEnum getOfficialIDTypeEnum() {
        return officialIDTypeEnum;
    }

    public void setOfficialIDTypeEnum(OfficialIdTypeEnum officialIDTypeEnum) {
        this.officialIDTypeEnum = officialIDTypeEnum;
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

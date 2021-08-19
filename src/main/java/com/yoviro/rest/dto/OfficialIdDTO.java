package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoviro.rest.config.enums.OfficialIdType;

public class OfficialIdDTO {
    @JsonIgnore
    private Long id;
    @JsonBackReference
    private ContactDTO contact;
    private OfficialIdType officialIdType;
    private String officialIdNumber;
    private Boolean primaryOfficialId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }

    public Boolean getPrimaryOfficialId() {
        return primaryOfficialId;
    }

    public void setPrimaryOfficialId(Boolean primaryOfficialId) {
        this.primaryOfficialId = primaryOfficialId;
    }

    public OfficialIdType getOfficialIdType() {
        return officialIdType;
    }

    public void setOfficialIdType(OfficialIdType officialIdType) {
        this.officialIdType = officialIdType;
    }

    public String getOfficialIdNumber() {
        return officialIdNumber;
    }

    public void setOfficialIdNumber(String officialIdNumber) {
        this.officialIdNumber = officialIdNumber;
    }
}

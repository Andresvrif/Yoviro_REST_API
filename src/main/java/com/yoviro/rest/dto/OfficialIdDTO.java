package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OfficialIdDTO {
    @JsonIgnore
    private Long id;
    @JsonBackReference
    private ContactDTO contact;
    private String officialIdType;
    private String officialIdNumber;

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

    public String getOfficialIdType() {
        return officialIdType;
    }

    public void setOfficialIdType(String officialIdType) {
        this.officialIdType = officialIdType;
    }

    public String getOfficialIdNumber() {
        return officialIdNumber;
    }

    public void setOfficialIdNumber(String officialIdNumber) {
        this.officialIdNumber = officialIdNumber;
    }
}

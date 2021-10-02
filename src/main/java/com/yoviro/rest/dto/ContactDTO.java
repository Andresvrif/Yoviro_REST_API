package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public abstract class ContactDTO {

    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String name;
    private String email;
    @JsonManagedReference
    @JsonProperty("officialIdDTOs")
    private List<OfficialIdDTO> officialIds;
    @JsonIgnore
    private Boolean internal;
    @JsonIgnore
    private Date createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OfficialIdDTO> getOfficialIds() {
        return officialIds;
    }

    public void setOfficialIds(List<OfficialIdDTO> officialIds) {
        this.officialIds = officialIds;
        for (OfficialIdDTO officialID : this.officialIds) {
            officialID.setContact(this);
        }
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Boolean getInternal() {
        return internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }
}
package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ResidentDTO {
    private Long id;

    @JsonProperty("contactDTO")
    private ContactDTO contact; //Paciente Adulto Mayor

    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Boolean enable;

    public ResidentDTO() {
    }

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}

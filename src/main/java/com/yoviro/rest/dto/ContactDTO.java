package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ContactDTO {

    @JsonIgnore
    private Long id;

    private String firstName;

    private String secondName;

    private String lastName;

    private String secondLastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String email;

    private String photo;

    @JsonManagedReference
    @JsonProperty("officialIdDTOs")
    private List<OfficialIdDTO> officialIds;

    private Date createAt;

    @JsonIgnore
    private Boolean internal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
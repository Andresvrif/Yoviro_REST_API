package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.*;
import java.util.Date;
import java.util.List;

public class ContactDTO {

    @JsonIgnore
    private Long id;

    private String firstName;

    private String secondName;

    private String firstLastName;

    private String secondLastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthDate;

    private String email;

    private String photo;

    @JsonManagedReference
    @JsonProperty("officialIdDTOs")
    private List<OfficialIdDTO> officialIds;

    private Date createAt;

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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
}
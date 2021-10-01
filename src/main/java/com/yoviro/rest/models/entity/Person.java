package com.yoviro.rest.models.entity;

import com.yoviro.rest.util.StringUtil;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("PERSON")
public class Person extends Contact {
    private String secondName;

    @Column(nullable = false)
    private String lastName;

    @NotEmpty
    @NotNull
    @Column
    private String secondLastName;

    @Column(nullable = false)
    private LocalDate birthDate;

    private String photo;

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        if (this.secondName != null) {
            this.secondName = this.secondName.toLowerCase();
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (this.lastName != null) {
            this.lastName = this.lastName.toLowerCase();
        }
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        if (this.secondLastName != null) {
            this.secondLastName = this.secondLastName.toLowerCase();
        }
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFullName() {
        String fullName = this.getName().concat(" ");
        if (getSecondName() != null) {
            fullName = fullName.concat(getSecondName()).concat(" ");
        }

        if (getLastName() != null) {
            fullName = fullName.concat(getLastName().concat(" "));
        }

        if (getSecondLastName() != null) {
            fullName = fullName.concat(getSecondLastName().concat(" "));
        }

        return StringUtil.capitalizeWord(fullName);
    }
}
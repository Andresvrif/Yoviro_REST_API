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

    @NotNull
    private String lastName;

    @NotEmpty
    @NotNull
    @Column
    private String secondLastName;

    @NotNull
    private LocalDate birthDate;

    private String photo;

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        if (secondName != null) {
            this.secondName = secondName.toLowerCase();
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null) {
            this.lastName = lastName.toLowerCase();
        }
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        if (secondLastName != null) {
            this.secondLastName = secondLastName.toLowerCase();
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
        String fullName = null;
        if (getName() != null) {
            fullName = fullName == null ? getName().concat(" ") : fullName.concat(getName()).concat(" ");
        }

        if (getSecondName() != null) {
            fullName = fullName == null ? getSecondName().concat(" ") : fullName.concat(getSecondName()).concat(" ");
        }

        if (getLastName() != null) {
            fullName = fullName == null ? getLastName().concat(" ") : fullName.concat(getLastName()).concat(" ");
        }

        if (getSecondLastName() != null) {
            fullName = fullName == null ? getSecondLastName().concat(" ") : fullName.concat(getSecondLastName().concat(" "));
        }

        return fullName == null ? fullName : StringUtil.capitalizeWord(fullName);
    }
}
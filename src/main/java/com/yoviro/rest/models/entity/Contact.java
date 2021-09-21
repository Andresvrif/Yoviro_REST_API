package com.yoviro.rest.models.entity;

import com.yoviro.rest.util.StringUtil;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contact")
@NamedQuery(name = "Contact.findByOfficialID",
        query = "select c from Contact as c " +
                "inner join OfficialId as o " +
                "ON o.contact.id=c.id where o.officialIdType=?1 and " +
                "o.officialIdNumber=?2")
public class Contact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String secondName;

    @Column(nullable = false)
    private String lastName;

    @NotEmpty
    @NotNull
    @Column
    private String secondLastName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Email
    private String email;

    private String photo;

    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "contact",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REMOVE
            },
            orphanRemoval = true)
    private List<OfficialId> officialIds;

    @Column(columnDefinition = "boolean default false")
    private Boolean internal;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    @PrePersist
    public void PrePersist() {
        this.createAt = DateUtils.createNow().getTime();
        if (this.firstName != null) {
            this.firstName = this.firstName.toLowerCase();
        }

        if (this.secondName != null) {
            this.secondName = this.secondName.toLowerCase();
        }

        if (this.lastName != null) {
            this.lastName = this.lastName.toLowerCase();
        }

        if (this.secondLastName != null) {
            this.secondLastName = this.secondLastName.toLowerCase();
        }

        if (this.officialIds != null) {
            if (this.officialIds.size() == 1) {
                OfficialId officialId = this.officialIds.get(0);
                officialId.setPrimaryOfficialId(true);
            }
        }
    }

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

    public void setLastName(String surname) {
        this.lastName = surname;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondSurname) {
        this.secondLastName = secondSurname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
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

    public List<OfficialId> getOfficialIds() {
        return officialIds;
    }

    public void setOfficialIds(List<OfficialId> officialIds) {
        this.officialIds = officialIds;
        for (OfficialId officialId : this.officialIds) {
            officialId.setContact(this);
        }
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getFullName() {
        String fullName = getFirstName().concat(" ");
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

    public OfficialId getPrimaryOfficialID() {
        if (officialIds == null) return null;
        for (OfficialId officialId : officialIds) {
            if (officialId.getPrimaryOfficialId()) return officialId;
        }
        return null;
    }

    public Boolean getInternal() {
        return internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    private static final long serialVersionUID = 1L;
}
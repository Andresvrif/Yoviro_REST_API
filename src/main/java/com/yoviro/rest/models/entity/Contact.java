package com.yoviro.rest.models.entity;

import com.yoviro.rest.dto.ContactDTO;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contact")
@NamedQuery(name = "Contact.findByOfficialID",
            query = "select c from Contact as c inner join OfficialId as o ON o.contact.id=c.id where o.officialIdType=?1 and o.officialIdNumber=?2")
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    private String firstName;

    private String secondName;

    @NotEmpty
    @NotNull
    private String firstLastName;

    @NotEmpty
    @NotNull
    @Column
    private String secondLastName;

    @NotNull
    @Column
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Email
    private String email;

    private String photo;

    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfficialId> officialIds;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    @PrePersist
    public void PrePersist() {
        this.createAt = DateUtils.createNow().getTime();
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

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String surname) {
        this.firstLastName = surname;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondSurname) {
        this.secondLastName = secondSurname;
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

    public List<OfficialId> getOfficialIds() {
        return officialIds;
    }

    public void setOfficialIds(List<OfficialId> officialIds) {
        this.officialIds = officialIds;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    private static final long serialVersionUID = 1L;
}
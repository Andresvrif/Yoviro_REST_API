package com.yoviro.rest.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contact")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQuery(name = "Contact.findByOfficialID",
        query = "select c from Contact as c " +
                "inner join OfficialId as o " +
                "ON o.contact =c where o.officialIdType=?1 and " +
                "o.officialIdNumber=?2")
@DiscriminatorColumn(name = "contactType", discriminatorType = DiscriminatorType.STRING)
public abstract class Contact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; //For Person is firstName, For Company is Name of the company

    @Email
    private String email;

    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "contact",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.REMOVE
            },
            orphanRemoval = true)
    private List<OfficialId> officialIds = new ArrayList<>();

    @Column(columnDefinition = "boolean default false")
    private Boolean internal;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    @PrePersist
    public void PrePersist() {
        if (this.officialIds != null) {
            if (this.officialIds.size() == 1) {
                OfficialId officialId = this.officialIds.get(0);
                officialId.setPrimaryOfficialId(true);
            }
        }

        if (this.internal == null) {
            this.internal = Boolean.FALSE;
        }

        this.createAt = DateUtils.createNow().getTime();
    }

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
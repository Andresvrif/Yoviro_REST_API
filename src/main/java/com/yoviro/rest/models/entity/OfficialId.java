package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.OfficialIdTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "officialId", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"officialIdType","officialIdNumber"})
})
public class OfficialId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Column(columnDefinition="tinyint(1) default 0")
    private Boolean primaryOfficialId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OfficialIdTypeEnum officialIdType;

    @NotNull
    private String officialIdNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Boolean getPrimaryOfficialId() {
        return primaryOfficialId;
    }

    public void setPrimaryOfficialId(Boolean primaryOfficialId) {
        this.primaryOfficialId = primaryOfficialId;
    }

    public OfficialIdTypeEnum getOfficialIdType() {
        return officialIdType;
    }

    public void setOfficialIdType(OfficialIdTypeEnum officialIdTypeEnum) {
        this.officialIdType = officialIdTypeEnum;
    }

    public String getOfficialIdNumber() {
        return officialIdNumber;
    }

    public void setOfficialIdNumber(String officialIdNumber) {
        this.officialIdNumber = officialIdNumber;
    }

    private static final long serialVersionUID = 1L;
}

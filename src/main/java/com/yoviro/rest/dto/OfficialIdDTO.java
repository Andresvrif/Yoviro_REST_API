package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.models.entity.OfficialId;

public class OfficialIdDTO {
    private Long id;
    @JsonBackReference
    private ContactDTO contact;
    private OfficialIdTypeEnum officialIdTypeEnum;
    private String officialIdNumber;
    private Boolean primaryOfficialId;

    public static OfficialIdDTO instanceFromEntity(OfficialId officialId) {
        OfficialIdDTO officialIdDTO = new OfficialIdDTO();
        officialIdDTO.setId(officialId.getId());
        officialIdDTO.setPrimaryOfficialId(officialId.getPrimaryOfficialId());
        officialIdDTO.setOfficialIdType(officialId.getOfficialIdType());
        officialIdDTO.setOfficialIdNumber(officialId.getOfficialIdNumber());

        return officialIdDTO;
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

    public Boolean getPrimaryOfficialId() {
        return primaryOfficialId;
    }

    public void setPrimaryOfficialId(Boolean primaryOfficialId) {
        this.primaryOfficialId = primaryOfficialId;
    }

    public OfficialIdTypeEnum getOfficialIdType() {
        return officialIdTypeEnum;
    }

    public void setOfficialIdType(OfficialIdTypeEnum officialIdTypeEnum) {
        this.officialIdTypeEnum = officialIdTypeEnum;
    }

    public String getOfficialIdNumber() {
        return officialIdNumber;
    }

    public void setOfficialIdNumber(String officialIdNumber) {
        this.officialIdNumber = officialIdNumber;
    }
}

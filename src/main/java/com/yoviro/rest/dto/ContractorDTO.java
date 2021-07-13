package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

public class ContractorDTO {
    private Long id;
    private String contractorNumber;

    @JsonProperty("contactDTO")
    //@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
    private ContactDTO contact;

    @JsonManagedReference
    private List<AgreementDTO> agreements;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractorNumber() {
        return contractorNumber;
    }

    public void setContractorNumber(String contractorNumber) {
        this.contractorNumber = contractorNumber;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }

    public List<AgreementDTO> getAgreements() {
        return agreements;
    }

    public void setAgreements(List<AgreementDTO> agreements) {
        this.agreements = agreements;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContractorDTO {
    @JsonIgnore
    private Long id;
    private String contractorNumber;

    @JsonProperty("contactDTO")
    private ContactDTO contact;

    @JsonProperty("agreementDTOs")
    @JsonManagedReference
    private List<AgreementDTO> agreements;

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

    public void addAgreement(AgreementDTO agreement) {
        this.agreements = this.agreements == null? new ArrayList<AgreementDTO>() : this.agreements;
        this.agreements.add(agreement);
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
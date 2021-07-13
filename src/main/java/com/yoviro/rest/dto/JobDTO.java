package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class JobDTO {
    private Long id;

    @JsonBackReference
    private AgreementDTO agreement;

    private String jobNumber;

    @JsonProperty("residentDTO")
    private ResidentDTO resident;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date effectiveDate;

    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AgreementDTO getAgreement() {
        return agreement;
    }

    public void setAgreement(AgreementDTO agreement) {
        this.agreement = agreement;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public ResidentDTO getResident() {
        return resident;
    }

    public void setResident(ResidentDTO resident) {
        this.resident = resident;
    }
}
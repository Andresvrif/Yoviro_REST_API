package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.*;
import com.yoviro.rest.models.entity.Job;
import com.yoviro.rest.util.DateUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public abstract class JobDTO {
    @JsonIgnore
    private Long id;

    @JsonBackReference
    private AgreementDTO agreement;

    private String jobNumber;

    @JsonProperty("residentDTO")
    private ResidentDTO resident;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime effectiveDate;

    public JobDTO() {
    }

    @JsonCreator
    public JobDTO(Long id,
                  String jobNumber,
                  String startDate,
                  String endDate,
                  String effectiveDate,
                  ResidentDTO residentDTO,
                  AgreementDTO agreementDTO) {
        this.id = id;
        this.jobNumber = jobNumber;
        this.startDate = startDate != null ? DateUtil.instanceEffectiveDateTime(DateUtil.instanceLocalDate(startDate, DateTimeFormatter.ISO_LOCAL_DATE)) : null;
        this.endDate = endDate != null ? DateUtil.instanceEffectiveDateTime(DateUtil.instanceLocalDate(endDate, DateTimeFormatter.ISO_LOCAL_DATE)) : null;
        this.effectiveDate = effectiveDate != null ? DateUtil.instanceEffectiveDateTime(DateUtil.instanceLocalDate(effectiveDate, DateTimeFormatter.ISO_LOCAL_DATE)) : null;
        this.resident = residentDTO;
        this.agreement = agreementDTO;
    }

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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public ResidentDTO getResident() {
        return resident;
    }

    public void setResident(ResidentDTO resident) {
        this.resident = resident;
    }
}
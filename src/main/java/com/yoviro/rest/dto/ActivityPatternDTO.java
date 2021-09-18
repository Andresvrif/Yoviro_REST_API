package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoviro.rest.config.enums.FormRequestedEnum;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class ActivityPatternDTO {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private LocalDateTime createAt;
    private String patternCode; //Unique
    private Integer dayFrequency; //Cada 2 dias, cada 3 dias, etc...
    private LocalDate startDate;
    private LocalDate endDate; //If it's null, end date
    private String subject;
    private String description;
    private Boolean enable;
    private Set<AgreementDTO> agreements;
    private FormRequestedEnum relatedForm;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime hourFrequency;

    private Set<AgreementDTO> agreementDTOs;

    private String colorCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatternCode() {
        return patternCode;
    }

    public void setPatternCode(String patternCode) {
        this.patternCode = patternCode;
    }

    public Integer getDayFrequency() {
        return dayFrequency;
    }

    public void setDayFrequency(Integer dayFrequency) {
        this.dayFrequency = dayFrequency;
    }

    public LocalTime getHourFrequency() {
        return hourFrequency;
    }

    public void setHourFrequency(LocalTime hourFrequency) {
        this.hourFrequency = hourFrequency;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<AgreementDTO> getAgreements() {
        return agreements;
    }

    public void setAgreements(Set<AgreementDTO> agreements) {
        this.agreements = agreements;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Set<AgreementDTO> getAgreementDTOs() {
        return agreementDTOs;
    }

    public void setAgreementDTOs(Set<AgreementDTO> agreementDTOs) {
        this.agreementDTOs = agreementDTOs;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public FormRequestedEnum getRelatedForm() {
        return relatedForm;
    }

    public void setRelatedForm(FormRequestedEnum relatedForm) {
        this.relatedForm = relatedForm;
    }
}
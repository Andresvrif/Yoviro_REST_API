package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Set;

public class ActivityPatternDTO {
    private Long id;
    private Date createAt;
    private String patternCode;
    private String dayFrequency;
    private Date hourFrequency;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date endDate;
    private String subject;
    private String description;
    private Set<AgreementDTO> agreements;
    private Boolean enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getPatternCode() {
        return patternCode;
    }

    public void setPatternCode(String patternCode) {
        this.patternCode = patternCode;
    }

    public String getDayFrequency() {
        return dayFrequency;
    }

    public void setDayFrequency(String dayFrequency) {
        this.dayFrequency = dayFrequency;
    }

    public Date getHourFrequency() {
        return hourFrequency;
    }

    public void setHourFrequency(Date hourFrequency) {
        this.hourFrequency = hourFrequency;
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

    public Set<AgreementDTO> getAgreements() {
        return agreements;
    }

    public void setAgreements(Set<AgreementDTO> agreements) {
        this.agreements = agreements;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}

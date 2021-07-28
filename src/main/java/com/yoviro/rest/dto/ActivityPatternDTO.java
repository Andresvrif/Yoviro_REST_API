package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Set;

public class ActivityPatternDTO {
    @JsonIgnore
    private Long id;
    private Date createAt;
    private String patternCode; //Unique
    private String dayFrequency; //Cada 2 dias, cada 3 dias, etc...

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date endDate; //If it's null, end date
    private String subject; //
    private String description;
    private Boolean enable;
    private Set<AgreementDTO> agreements;
    private Date hourFrequency;

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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Set<AgreementDTO> getAgreements() {
        return agreements;
    }

    public void setAgreements(Set<AgreementDTO> agreements) {
        this.agreements = agreements;
    }
}
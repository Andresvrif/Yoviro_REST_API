package com.yoviro.rest.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class ActivityPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    private String patternCode;

    private String dayFrequency; //Cada 2 dias, cada 3 dias, etc...

    private Date startDate;

    private Date endDate; //If it's null, end date

    private String subject; //

    private String description; //

    @ManyToMany(fetch = FetchType.LAZY,
                mappedBy = "activityPatterns")
    private Set<Agreement> agreements; //Contratos vigentes, como regla de negocio los contratos tendran vigencia de medio dia a medio dia

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

    public Set<Agreement> getAgreements() {
        return agreements;
    }

    public void setAgreements(Set<Agreement> agreements) {
        this.agreements = agreements;
    }

    private static final long serialVersionUID = 1L;
}

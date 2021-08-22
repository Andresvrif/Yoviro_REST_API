package com.yoviro.rest.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "activity_pattern", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"patternCode"})
})
@NamedQueries({
        @NamedQuery(name = "ActivityPattern.summaryList",
                query = "select ap.patternCode, ap.enable, ap.subject, ap.startDate, ap.endDate, ap.dayFrequency, ap.hourFrequency from ActivityPattern as ap"),
        @NamedQuery(name = "ActivityPattern.deleteByPatternCodes",
                query = "delete from ActivityPattern as ap where ap.patternCode in ?1"),
        @NamedQuery(name = "ActivityPattern.agreementsResidentRelated",
                query = "select distinct c.firstName as firstName," +
                        "c.secondName as secondName," +
                        "c.firstLastName as firstLastName," +
                        "c.secondLastName as secondLastName," +
                        "ag.agreementNumber as agreementNumber from ActivityPattern ap " +
                        "inner join ap.agreements aap on ap.patternCode = ?1 " +
                        "inner join Agreement ag on aap.id=ag.id " +
                        "inner join Job j on j.agreement=ag " +
                        "inner join Resident r on r=j.resident " +
                        "inner join Contact c on r.contact=c")
})
public class ActivityPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    private String patternCode;

    @NotNull
    private String dayFrequency; //Cada 2 dias, cada 3 dias, etc...

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date hourFrequency;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate; //If it's null, end date

    @NotNull
    private String subject;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "activityPatterns")
    private Set<Agreement> agreements; //Contratos vigentes, como regla de negocio los contratos tendran vigencia de medio dia a medio dia


    @OneToMany(mappedBy = "activityPattern",
               fetch = FetchType.LAZY)
    private Set<Activity> activities;

    @NotNull
    private Boolean enable;

    @PrePersist
    public void PrePersist() {
        this.createAt = DateUtils.createNow().getTime();
        if (this.subject != null) {
            this.subject = this.subject.toLowerCase();
        }

        if (this.description != null) {
            this.description = this.description.toLowerCase();
        }
    }

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

    public Date getHourFrequency() {
        return hourFrequency;
    }

    public void setHourFrequency(Date hourFrequency) {
        this.hourFrequency = hourFrequency;
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

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    private static final long serialVersionUID = 1L;
}

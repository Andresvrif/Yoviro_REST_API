package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.FormRequestedEnum;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
        @NamedQuery(name = "ActivityPattern.enableActivityPatterns",
                query = "select ap from ActivityPattern as ap where ap.enable = ?1"),
        @NamedQuery(name = "ActivityPattern.agreementsResidentRelated",
                query = "select distinct p.name as firstName, " +
                        "p.secondName as secondName, " +
                        "p.lastName as firstLastName, " +
                        "p.secondLastName as secondLastName, " +
                        "ag.agreementNumber as agreementNumber from ActivityPattern ap " +
                        "inner join ap.agreements aap on ap.patternCode = ?1 " +
                        "inner join Agreement ag on aap.id=ag.id " +
                        "inner join Job j on j.agreement=ag " +
                        "inner join Resident r on r=j.resident " +
                        "inner join Person p on r.person=p"),
        @NamedQuery(name ="ActivityPattern.enableActivityPatternsWithAgreements",
                query = "select distinct ap from ActivityPattern ap " +
                        "join fetch ap.agreements ag " +
                        "where ap.enable=true")
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
    private Integer dayFrequency; //Cada 2 dias, cada 3 dias, etc...

    @NotNull
    private LocalTime hourFrequency;

    @NotNull
    private LocalDate startDate;

    @Column
    private LocalDate endDate; //If it's null, end date

    @NotNull
    private String subject;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "activity_pattern_agreement",
            joinColumns = {@JoinColumn(name = "activity_pattern_id")},
            inverseJoinColumns = {@JoinColumn(name = "agreement_id")})
    private List<Agreement> agreements = new ArrayList<>(); //Contratos vigentes, como regla de negocio los contratos tendran vigencia de medio dia a medio dia

    @OneToMany(mappedBy = "activityPattern", fetch = FetchType.LAZY)
    private Set<Activity> activities;

    @NotNull
    private Boolean enable;

    @Column(nullable = true, columnDefinition = "varchar(255) default '#099EAE'")
    private String colorCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private FormRequestedEnum relatedForm;

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

    public List<Agreement> getAgreements() {
        return agreements;
    }

    public void setAgreements(List<Agreement> agreements) {
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

    public Boolean hasAgreements() {
        return agreements != null && agreements.size() > 0;
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

    private static final long serialVersionUID = 1L;
}

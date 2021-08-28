package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.StatusTerm;
import com.yoviro.rest.handler.JobHandler;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "agreement", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"agreementNumber"}),
        @UniqueConstraint(columnNames = {"contractor_id", "id"})
})
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Generated(GenerationTime.INSERT)
    private String agreementNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Contractor.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    @OneToMany(mappedBy = "agreement",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Job> jobs = new ArrayList<>();

    @NotNull
    @Column(name = "create_at")
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;
    //TODO evaluar establecer una property para definir el estado del contrato (planificado, en vigencia y/o)

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "agreement_activity_pattern",
            joinColumns = {@JoinColumn(name = "agreement_id")},
            inverseJoinColumns = {@JoinColumn(name = "activity_pattern_id")})
    private List<ActivityPattern> activityPatterns = new ArrayList<>();

    @PrePersist
    public void PrePersist() {
        this.createAt = DateUtils.createNow().getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<ActivityPattern> getActivityPatterns() {
        return activityPatterns;
    }

    public void setActivityPatterns(List<ActivityPattern> activityPatterns) {
        this.activityPatterns = activityPatterns;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Boolean hasJobs() {
        return jobs != null && jobs.size() > 0;
    }

    public StatusTerm getStatus(Date referenceDate) throws Exception {
        if (!hasJobs()) {
            throw new Exception("Can't define a status agreement without jobs defined");
        }
        Job lastJob = JobHandler.lastJobFromAgreement(this);
        return JobHandler.getStatusTerm(lastJob, referenceDate);
    }

    private static final long serialVersionUID = 1L;
}
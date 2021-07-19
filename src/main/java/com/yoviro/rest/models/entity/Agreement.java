package com.yoviro.rest.models.entity;

import com.yoviro.rest.dto.JobDTO;
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

    @OneToMany(mappedBy = "agreement", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs;

    @NotNull
    @Column(name = "create_at")
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;
    //TODO evaluar establecer una property para definir el estado del contrato (planificado, en vigencia y/o)

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "agreements",
    joinColumns = {@JoinColumn(name = "agreement_id")},
    inverseJoinColumns = {@JoinColumn(name = "activity_pattern_id")})
    private Set<ActivityPattern> activityPatterns;

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

    public void addJob(Job job) {
        this.jobs = this.jobs == null? new ArrayList<Job>() : this.jobs;
        this.jobs.add(job);
    }


    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    private static final long serialVersionUID = 1L;
}
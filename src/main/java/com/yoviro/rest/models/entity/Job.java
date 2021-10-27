package com.yoviro.rest.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "job", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"agreement_id", "resident_id", "id"})
})//Asegurar que solo exista esta solicitud para el contrato con el residente
@DiscriminatorColumn(name = "jobType", discriminatorType = DiscriminatorType.STRING)
public abstract class Job implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agreement_id")
    protected Agreement agreement;

    @Generated(GenerationTime.INSERT)
    protected String jobNumber;

    /***
     * Author : Andrés V.
     * Desc : Fecha de inicio del contrato
     */
    @Column(nullable = false)
    protected LocalDateTime startDate;

    /***
     * Author : Andrés V.
     * Desc : Fecha de fin del contrato
     */
    @Column(nullable = true)
    protected LocalDateTime endDate;

    /***
     * Author : Andrés V.
     * Desc : Fecha efectiva de la aplicación de la solicitud
     */
    @Column(nullable = false)
    protected LocalDateTime effectiveDate;

    /***
     * Author : Andrés V.
     * Desc : Datos del PAM alojado en la residencia
     */
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "resident_id")
    protected Resident resident;

    @Column(nullable = false)
    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    protected LocalDateTime createAt;

    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
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

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    protected static final long serialVersionUID = 1L;
}

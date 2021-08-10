package com.yoviro.rest.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_id")
    protected Agreement agreement;
    
    @Generated(GenerationTime.INSERT)
    protected String jobNumber;

    /***
     * Author : Andrés V.
     * Desc : Fecha de inicio del contrato
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    protected Date startDate;

    /***
     * Author : Andrés V.
     * Desc : Fecha de fin del contrato
     */
    @Temporal(TemporalType.TIMESTAMP)
    protected Date endDate;

    /***
     * Author : Andrés V.
     * Desc : Fecha efectiva de la aplicación de la solicitud
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    protected Date effectiveDate;

    /***
     * Author : Andrés V.
     * Desc : Datos del PAM alojado en la residencia
     */
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "resident_id")
    protected Resident resident;

    @NotNull
    @Column(name = "create_at")
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date createAt;

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

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    protected static final long serialVersionUID = 1L;
}

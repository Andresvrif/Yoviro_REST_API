package com.yoviro.rest.models.entity;

import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class VitalSignsCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal arterialPresion;

    @Column(nullable = false)
    private BigDecimal temperature;

    @Column(nullable = false)
    private BigDecimal oxygenation;

    @Column
    private BigDecimal glucose;

    @Column
    private String observation;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resident_id")
    private Resident resident;

    @PrePersist
    public void PrePersist() {
        //TODO
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getArterialPresion() {
        return arterialPresion;
    }

    public void setArterialPresion(BigDecimal arterialPresion) {
        this.arterialPresion = arterialPresion;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getOxygenation() {
        return oxygenation;
    }

    public void setOxygenation(BigDecimal oxygenation) {
        this.oxygenation = oxygenation;
    }

    public BigDecimal getGlucose() {
        return glucose;
    }

    public void setGlucose(BigDecimal glucose) {
        this.glucose = glucose;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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

    private static final long serialVersionUID = 1L;
}
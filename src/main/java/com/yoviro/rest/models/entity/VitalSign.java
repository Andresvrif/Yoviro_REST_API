package com.yoviro.rest.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "VitalSign.summaryListAll",
                query = "select vs.id as id," +
                        "p.name as firstName," +
                        "p.secondName as secondName," +
                        "p.lastName as lastName," +
                        "p.secondLastName as secondLastName," +
                        "vs.arterialPresion as arterialPresion," +
                        "vs.oxygenation as oxygenation," +
                        "vs.temperature as temperature," +
                        "vs.createAt as createAt from VitalSign vs " +
                        "join Resident r on r = vs.resident " +
                        "join Person p on r.person = p"),
        @NamedQuery(name = "VitalSign.summaryListLikeFirstName",
                query = "select vs.id as id," +
                        "p.name as firstName," +
                        "p.secondName as secondName," +
                        "p.lastName as lastName," +
                        "p.secondLastName as secondLastName," +
                        "vs.arterialPresion as arterialPresion," +
                        "vs.oxygenation as oxygenation," +
                        "vs.temperature as temperature," +
                        "vs.createAt as createAt from VitalSign vs " +
                        "join Resident r on r = vs.resident " +
                        "join Person p on r.person = p " +
                        "where p.name like ?1")
})
public class VitalSign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String arterialPresion;

    @Column(nullable = false)
    private BigDecimal temperature;

    @Column(nullable = false)
    private BigDecimal oxygenation;

    @Column
    private BigDecimal glucose;

    @Column
    private String observation;

    @Column(nullable = false)
    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private LocalDateTime createAt;

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

    public String getArterialPresion() {
        return arterialPresion;
    }

    public void setArterialPresion(String arterialPresion) {
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    private static final long serialVersionUID = 1L;
}
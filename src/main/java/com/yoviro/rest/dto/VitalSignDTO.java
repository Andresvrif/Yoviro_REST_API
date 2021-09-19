package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class VitalSignDTO {

    @JsonIgnore
    private Long id;

    private String arterialPresion;

    private BigDecimal temperature;

    private BigDecimal oxygenation;

    private BigDecimal glucose;

    private String observation;

    private ResidentDTO residentDTO;

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

    public ResidentDTO getResidentDTO() {
        return residentDTO;
    }

    public void setResidentDTO(ResidentDTO residentDTO) {
        this.residentDTO = residentDTO;
    }
}
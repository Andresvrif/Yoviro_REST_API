package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

public class AgreementDTO {
    private Long id;
    private String agreementNumber;
    @JsonBackReference
    private ContractorDTO contractor;
    private List<JobDTO> solicitudes;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

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

    public ContractorDTO getContractor() {
        return contractor;
    }

    public void setContractor(ContractorDTO contractor) {
        this.contractor = contractor;
    }

    public List<JobDTO> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<JobDTO> solicitudes) {
        this.solicitudes = solicitudes;
    }
}

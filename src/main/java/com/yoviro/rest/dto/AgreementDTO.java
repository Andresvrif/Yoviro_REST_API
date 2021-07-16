package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgreementDTO {
    @JsonIgnore
    private Long id;

    private String agreementNumber;

    @JsonBackReference
    private ContractorDTO contractor;

    @JsonProperty("jobDTOs")
    @JsonManagedReference
    private List<JobDTO> jobs;

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

    public List<JobDTO> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDTO> jobs) {
        this.jobs = jobs;
    }

    public void addJob(JobDTO job) {
        this.jobs = this.jobs == null? new ArrayList<JobDTO>() : this.jobs;
        this.jobs.add(job);
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}

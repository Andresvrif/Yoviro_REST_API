package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.*;
import com.yoviro.rest.models.entity.ActivityPattern;

import java.util.*;

public class AgreementDTO {
    @JsonIgnore
    private Long id;

    private String agreementNumber;

    @JsonBackReference
    private ContractorDTO contractor;

    @JsonProperty("jobDTOs")
    @JsonManagedReference
    private List<JobDTO> jobs = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    private Set<ActivityPatternDTO> activityPatternDTOs;


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
        this.jobs = new ArrayList<>();
        if (jobs == null) return;
        for (JobDTO jobDTO : jobs) {
            if (jobDTO.getAgreement() == null) {
                jobDTO.setAgreement(this);
            }
            this.jobs.add(jobDTO);
        }
    }

    public Set<ActivityPatternDTO> getActivityPatternDTOs() {
        return activityPatternDTOs;
    }

    public void setActivityPatternDTOs(Set<ActivityPatternDTO> activityPatternDTOs) {
        this.activityPatternDTOs = activityPatternDTOs;
    }

    public void addActivityPattern(ActivityPatternDTO activityPatternDTO) {
        this.activityPatternDTOs = this.activityPatternDTOs == null ? new HashSet<ActivityPatternDTO>() : this.activityPatternDTOs;
        this.activityPatternDTOs.add(activityPatternDTO);
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}

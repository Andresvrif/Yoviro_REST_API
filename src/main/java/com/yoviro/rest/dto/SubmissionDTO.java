package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SubmissionDTO extends JobDTO {
    public SubmissionDTO() {
        super();
    }

    @JsonCreator
    public SubmissionDTO(Long id, String jobNumber, String startDate, String endDate, String effectiveDate, ResidentDTO residentDTO, AgreementDTO agreementDTO) {
        super(id, jobNumber, startDate, endDate, effectiveDate, residentDTO, agreementDTO);
    }
}

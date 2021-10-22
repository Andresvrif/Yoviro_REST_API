package com.yoviro.rest.dto.search;

import com.yoviro.rest.config.enums.StatusProposalEnum;

public class SearchProposalDTO {
    protected String proposalNumber;
    private StatusProposalEnum status;

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public StatusProposalEnum getStatus() {
        return status;
    }

    public void setStatus(StatusProposalEnum status) {
        this.status = status;
    }
}
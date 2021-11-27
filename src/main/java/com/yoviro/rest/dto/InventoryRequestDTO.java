package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import com.yoviro.rest.models.entity.InventoryRequestDetail;
import com.yoviro.rest.models.entity.Proposal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryRequestDTO {
    @JsonIgnore
    private Long id;
    protected String inventoryRequestNumber;
    protected WorkerDTO author;
    protected ResidentDTO resident;
    protected InventoryRequestStatusEnum status;
    private List<InventoryRequestDetailDTO> details = new ArrayList<>();
    private List<ProposalDTO> proposals = new ArrayList<>();
    private String reasonForDenied;
    protected LocalDateTime createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInventoryRequestNumber() {
        return inventoryRequestNumber;
    }

    public void setInventoryRequestNumber(String inventoryRequestNumber) {
        this.inventoryRequestNumber = inventoryRequestNumber;
    }

    public WorkerDTO getAuthor() {
        return author;
    }

    public void setAuthor(WorkerDTO author) {
        this.author = author;
    }

    public ResidentDTO getResident() {
        return resident;
    }

    public void setResident(ResidentDTO resident) {
        this.resident = resident;
    }

    public InventoryRequestStatusEnum getStatus() {
        return status;
    }

    public void setStatus(InventoryRequestStatusEnum status) {
        this.status = status;
    }

    public List<InventoryRequestDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<InventoryRequestDetailDTO> details) {
        this.details = details;
    }

    public List<ProposalDTO> getProposals() {
        return proposals;
    }

    public void setProposals(List<ProposalDTO> proposals) {
        this.proposals = proposals;
    }

    public String getReasonForDenied() {
        return reasonForDenied;
    }

    public void setReasonForDenied(String reasonForDenied) {
        this.reasonForDenied = reasonForDenied;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}

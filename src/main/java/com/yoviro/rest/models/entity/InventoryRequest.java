package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class InventoryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id")
    private Resident resident;

    @NotNull
    @Enumerated(EnumType.STRING)
    private InventoryRequestStatusEnum status;

    @NotNull
    @OneToMany(mappedBy = "inventoryRequest", fetch = FetchType.LAZY)
    private List<InventoryRequestDetail> details = new ArrayList<>();

    @NotNull
    @ManyToMany(mappedBy = "inventoryRequests")
    private List<Proposal> proposals = new ArrayList<>();

    @NotNull
    private LocalDateTime createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public InventoryRequestStatusEnum getStatus() {
        return status;
    }

    public void setStatus(InventoryRequestStatusEnum status) {
        this.status = status;
    }

    public List<InventoryRequestDetail> getDetails() {
        return details;
    }

    public void setDetails(List<InventoryRequestDetail> details) {
        this.details = details;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    private static final long serialVersionUID = 1L;
}

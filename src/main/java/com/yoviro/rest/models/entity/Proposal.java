package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.StatusProposalEnum;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Generated(GenerationTime.INSERT)
    protected String proposalNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private StoreKeeper author;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private LocalDateTime createAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusProposalEnum status;

    @Column
    private String rejectedReason;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "inventory_request_proposal",
            joinColumns = {@JoinColumn(name = "proposal_id")},
            inverseJoinColumns = {@JoinColumn(name = "inventory_request_id")})
    private List<InventoryRequest> inventoryRequests = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "purchase_order_proposal",
            joinColumns = {@JoinColumn(name = "proposal_id")},
            inverseJoinColumns = {@JoinColumn(name = "purchase_order_id")})
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public StoreKeeper getAuthor() {
        return author;
    }

    public void setAuthor(StoreKeeper author) {
        this.author = author;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public List<InventoryRequest> getInventoryRequests() {
        return inventoryRequests;
    }

    public void setInventoryRequests(List<InventoryRequest> inventoryRequests) {
        this.inventoryRequests = inventoryRequests;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public StatusProposalEnum getStatus() {
        return status;
    }

    public void setStatus(StatusProposalEnum status) {
        this.status = status;
    }

    @PrePersist
    public void PrePersist() {
        this.createAt = LocalDateTime.now();
    }

    private static final long serialVersionUID = 1L;
}

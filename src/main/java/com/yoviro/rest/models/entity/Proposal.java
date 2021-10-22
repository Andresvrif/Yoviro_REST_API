package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.StatusProposalEnum;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.spi.MonetaryCurrenciesSingletonSpi;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_keeper_id", nullable = false)
    private StoreKeeper author;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "director_id", nullable = true)
    private Director evaluator;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private LocalDateTime createAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private LocalDateTime updateAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusProposalEnum status;

    @Column
    private String reasonForDenied; //Can be rejected or canceled

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

    public Director getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Director evaluator) {
        this.evaluator = evaluator;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getReasonForDenied() {
        return reasonForDenied;
    }

    public void setReasonForDenied(String rejectedReason) {
        this.reasonForDenied = rejectedReason;
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
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    public void PreUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    public MonetaryAmount getTotalPrice() {
        if (this.purchaseOrders.isEmpty()) {
            return Money.of(BigDecimal.ZERO, "PEN");
        }

        MonetaryAmount totalPrice = Money.of(BigDecimal.ZERO, "PEN");
        MonetaryAmount aux = null;
        for (PurchaseOrder purchaseOrder : this.purchaseOrders) {
            aux = Money.of(purchaseOrder.getTotalPrice(), "PEN");
            totalPrice = totalPrice.add(aux);
        }

        return totalPrice;
    }

    private static final long serialVersionUID = 1L;
}

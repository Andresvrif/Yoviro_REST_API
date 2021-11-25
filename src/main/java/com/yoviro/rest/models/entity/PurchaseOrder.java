package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.PurcharseOrderEnum;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String purchaseOrderNumber; //Número generado para manejo interno

    private String referenceNumber; //Reference Number

    private String attachmentDocument;

    @Enumerated(EnumType.STRING)
    private PurcharseOrderEnum status;

    @Column
    private String reasonForDenied; //Can be rejected or canceled

    @NotNull
    @ManyToOne
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private StoreKeeper author;

    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PurchaseOrderDetail> details = new ArrayList<>();

    @NotNull
    @ManyToMany(mappedBy = "purchaseOrders")
    private List<Proposal> proposals = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    List<InputTransactionByPurchaseOrder> inputTransactionsByPurchaseOrder = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    List<OutputTransactionByPurchaseOrder> outputTransactionsByPurchaseOrder = new ArrayList<>();

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private LocalDateTime createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getAttachmentDocument() {
        return attachmentDocument;
    }

    public void setAttachmentDocument(String attachmentDocument) {
        this.attachmentDocument = attachmentDocument;
    }

    public PurcharseOrderEnum getStatus() {
        return status;
    }

    public void setStatus(PurcharseOrderEnum status) {
        this.status = status;
    }

    public StoreKeeper getAuthor() {
        return author;
    }

    public void setAuthor(StoreKeeper author) {
        this.author = author;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<PurchaseOrderDetail> getPurcharseOrderDetails() {
        return details;
    }

    public void setPurcharseOrderDetails(List<PurchaseOrderDetail> purchaseOrderDetails) {
        this.details = purchaseOrderDetails;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public List<InputTransactionByPurchaseOrder> getInputTransactionsByPurchaseOrder() {
        return inputTransactionsByPurchaseOrder;
    }

    public void setInputTransactionsByPurchaseOrder(List<InputTransactionByPurchaseOrder> inputTransactionsByPurchaseOrder) {
        this.inputTransactionsByPurchaseOrder = inputTransactionsByPurchaseOrder;
    }

    public List<OutputTransactionByPurchaseOrder> getOutputTransactionsByPurchaseOrder() {
        return outputTransactionsByPurchaseOrder;
    }

    public void setOutputTransactionsByPurchaseOrder(List<OutputTransactionByPurchaseOrder> outputTransactionsByPurchaseOrder) {
        this.outputTransactionsByPurchaseOrder = outputTransactionsByPurchaseOrder;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @PrePersist
    public void PrePersist() {
        this.createAt = LocalDateTime.now();
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public List<PurchaseOrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PurchaseOrderDetail> details) {
        this.details = details;
    }

    public String getReasonForDenied() {
        return reasonForDenied;
    }

    public void setReasonForDenied(String reasonForDenied) {
        this.reasonForDenied = reasonForDenied;
    }

    private static final long serialVersionUID = 1L;
}

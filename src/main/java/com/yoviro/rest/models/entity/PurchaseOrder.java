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

    private String purchaseOrderNumber;

    private String attachmentDocument;

    @Enumerated(EnumType.STRING)
    private PurcharseOrderEnum status;

    @ManyToOne
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private StoreKeeper author;

    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    private List<PurchaseOrderDetail> purchaseOrderDetails = new ArrayList<>();

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
        return purchaseOrderDetails;
    }

    public void setPurcharseOrderDetails(List<PurchaseOrderDetail> purchaseOrderDetails) {
        this.purchaseOrderDetails = purchaseOrderDetails;
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

    private static final long serialVersionUID = 1L;
}

package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.PurcharseOrderEnum;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String attachmentDocument;

    private PurcharseOrderEnum status;

    @ManyToOne
    private Company company;

    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    private List<PurcharseOrderDetail> purcharseOrderDetails = new ArrayList<>();

    @NotNull
    @ManyToMany(mappedBy = "purchaseOrders")
    private List<Proposal> proposals = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    List<InputTransactionByPurchaseOrder> inputTransactionsByPurchaseOrder = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    List<OutputTransactionByPurchaseOrder> outputTransactionsByPurchaseOrder = new ArrayList<>();

    @NotNull
    private LocalDateTime createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<PurcharseOrderDetail> getPurcharseOrderDetails() {
        return purcharseOrderDetails;
    }

    public void setPurcharseOrderDetails(List<PurcharseOrderDetail> purcharseOrderDetails) {
        this.purcharseOrderDetails = purcharseOrderDetails;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    private static final long serialVersionUID = 1L;
}

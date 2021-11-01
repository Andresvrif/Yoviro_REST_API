package com.yoviro.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class ProposalDTO {
    private Long id;
    private String proposalNumber;
    private List<InventoryRequestDTO> inventoryRequests = new ArrayList<>();
    private List<PurchaseOrderDTO> purchaseOrders = new ArrayList<>();

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

    public List<InventoryRequestDTO> getInventoryRequests() {
        return inventoryRequests;
    }

    public void setInventoryRequests(List<InventoryRequestDTO> inventoryRequests) {
        this.inventoryRequests = inventoryRequests;
    }

    public List<PurchaseOrderDTO> getPurchaseOrders() {
        return purchaseOrders;
    }
    
    public void setPurchaseOrders(List<PurchaseOrderDTO> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
}
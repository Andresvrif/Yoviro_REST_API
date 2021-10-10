package com.yoviro.rest.models.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("STOREKEEPER")
public class StoreKeeper extends Worker {
    @OneToMany(mappedBy = "author",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Proposal> proposals = new ArrayList<>();

    @OneToMany(mappedBy = "author",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
}
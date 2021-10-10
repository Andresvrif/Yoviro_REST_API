package com.yoviro.rest.models.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("NURSE")
public class Nurse extends Worker {
    @OneToMany(mappedBy = "author",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<InventoryRequest> inventoryRequests = new ArrayList<>();

    public List<InventoryRequest> getInventoryRequests() {
        return inventoryRequests;
    }

    public void setInventoryRequests(List<InventoryRequest> inventoryRequests) {
        this.inventoryRequests = inventoryRequests;
    }
}
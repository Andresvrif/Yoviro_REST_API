package com.yoviro.rest.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("OUTPUT_BY_DISPATCH")
public class OutputTransactionByDispatcher extends InventoryTransaction {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_dispatch_id")
    ResidentDispatch residentDispatch;

    public ResidentDispatch getResidentDispatch() {
        return residentDispatch;
    }

    public void setResidentDispatch(ResidentDispatch residentDispatch) {
        this.residentDispatch = residentDispatch;
    }
}
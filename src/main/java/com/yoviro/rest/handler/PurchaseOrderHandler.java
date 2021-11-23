package com.yoviro.rest.handler;

import com.yoviro.rest.models.entity.Proposal;
import com.yoviro.rest.models.entity.PurchaseOrder;

import java.util.Comparator;
import java.util.List;

public class PurchaseOrderHandler {
    public static Proposal lastProposalFromPurchaseOrder(PurchaseOrder purchaseOrder) {
        List<Proposal> proposals = purchaseOrder.getProposals();
        if (proposals.isEmpty()) return null;
        proposals.sort(Comparator.comparing(Proposal::getId));
        return proposals.get(proposals.size() - 1);
    }
}
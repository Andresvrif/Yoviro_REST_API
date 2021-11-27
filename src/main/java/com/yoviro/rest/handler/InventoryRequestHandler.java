package com.yoviro.rest.handler;

import com.yoviro.rest.models.entity.InventoryRequest;
import com.yoviro.rest.models.entity.Proposal;
import java.util.Comparator;
import java.util.List;

public class InventoryRequestHandler {
    public static Proposal lastProposalFromInventoryRequest(InventoryRequest inventoryRequest) {
        List<Proposal> proposals = inventoryRequest.getProposals();
        if (proposals.isEmpty()) return null;
        proposals.sort(Comparator.comparing(Proposal::getId));
        return proposals.get(proposals.size() - 1);
    }
}

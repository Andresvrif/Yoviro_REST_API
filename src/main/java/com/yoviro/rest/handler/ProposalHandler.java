package com.yoviro.rest.handler;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import com.yoviro.rest.config.enums.PurcharseOrderEnum;
import com.yoviro.rest.models.entity.InventoryRequest;
import com.yoviro.rest.models.entity.Proposal;
import com.yoviro.rest.models.entity.PurchaseOrder;

import java.util.List;
import java.util.stream.Collectors;

public class ProposalHandler {
    /***
     * Author : Andrés V.
     * Desc : It Transfer all details like inventory requests & purchase orders without cancelled or rejected status
     * @param src
     * @param dest
     * @return
     */
    public static Proposal transferCleanDetails(Proposal src,
                                                Proposal dest) {
        //Copy Inventory Requests
        List<InventoryRequest> inventoryRequests = src.getInventoryRequests().stream().filter(e -> e.getStatus() != InventoryRequestStatusEnum.REJECTED).collect(Collectors.toList());
        dest.setInventoryRequests(inventoryRequests);
        inventoryRequests.forEach(e -> e.getProposals().add(dest));

        //Copy Purchase Orders
        List<PurchaseOrder> purchaseOrders = src.getPurchaseOrders().stream().filter(e -> e.getStatus() != PurcharseOrderEnum.CANCELLED && e.getStatus() != PurcharseOrderEnum.REJECTED).collect(Collectors.toList());
        dest.setPurchaseOrders(purchaseOrders);
        purchaseOrders.forEach(e -> e.getProposals().add(dest));
        return dest;
    }
}

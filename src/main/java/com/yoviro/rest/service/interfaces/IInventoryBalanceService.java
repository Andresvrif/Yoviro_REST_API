package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.models.entity.InventoryBalance;
import com.yoviro.rest.models.entity.PurchaseOrder;

import java.util.List;

public interface IInventoryBalanceService {
    List<InventoryBalance> processPurchaseOrder(PurchaseOrder purchaseOrder);
}

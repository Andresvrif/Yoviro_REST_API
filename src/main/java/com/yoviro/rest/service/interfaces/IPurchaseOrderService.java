package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.search.SearchPurchaseOrderDTO;
import com.yoviro.rest.models.entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPurchaseOrderService {
    Page<PurchaseOrder> search(Pageable pageable,
                               SearchPurchaseOrderDTO criteria);
    PurchaseOrder findByPurcharseOrderNumber(String purchaseOrderNumber);
}

package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.ProposalDTO;
import com.yoviro.rest.dto.PurchaseOrderDTO;
import com.yoviro.rest.dto.search.SearchPurchaseOrderDTO;
import com.yoviro.rest.models.entity.InventoryBalance;
import com.yoviro.rest.models.entity.Proposal;
import com.yoviro.rest.models.entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IPurchaseOrderService {
    Page<PurchaseOrder> search(Pageable pageable,
                               SearchPurchaseOrderDTO criteria);

    PurchaseOrder findByPurcharseOrderNumber(String purchaseOrderNumber);

    Proposal updateReferencesNumbers(String userName,
                                     ProposalDTO proposalDTO);

    public List<InventoryBalance> markAsReceived(String purchaseOrderNumber);
}

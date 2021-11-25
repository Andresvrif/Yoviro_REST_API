package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Proposal;
import com.yoviro.rest.models.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PurchaseOrderRepository  extends PagingAndSortingRepository<PurchaseOrder, Long>,
        JpaSpecificationExecutor<PurchaseOrder> {
    PurchaseOrder findByPurchaseOrderNumber(String purchaseOrderNumber);
    List<PurchaseOrder> findAllByPurchaseOrderNumberIn(List<String> purchaseOrderNumbers);
}

package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Proposal;
import com.yoviro.rest.models.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PurchaseOrderRepository  extends PagingAndSortingRepository<PurchaseOrder, Long>, JpaSpecificationExecutor<PurchaseOrder> {
}

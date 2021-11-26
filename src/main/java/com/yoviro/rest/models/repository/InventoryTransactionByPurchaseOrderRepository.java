package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.InputTransactionByPurchaseOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTransactionByPurchaseOrderRepository extends PagingAndSortingRepository<InputTransactionByPurchaseOrder, Long> {
}

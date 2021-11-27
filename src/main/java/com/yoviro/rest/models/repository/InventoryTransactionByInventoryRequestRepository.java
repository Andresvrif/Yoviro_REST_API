package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.InputTransactionByPurchaseOrder;
import com.yoviro.rest.models.entity.OutputTransactionByInventoryRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTransactionByInventoryRequestRepository extends PagingAndSortingRepository<OutputTransactionByInventoryRequest, Long> {
}

package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.InventoryRequest;
import com.yoviro.rest.models.entity.InventoryRequestDetail;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryRequestDetailRepository extends PagingAndSortingRepository<InventoryRequestDetail, Long> {

    void deleteAllByInventoryRequest(InventoryRequest inventoryRequest);
}

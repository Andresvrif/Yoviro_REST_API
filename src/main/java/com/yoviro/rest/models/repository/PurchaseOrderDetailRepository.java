package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PurchaseOrderDetailRepository  extends PagingAndSortingRepository<PurchaseOrderDetail, Long>, JpaSpecificationExecutor<PurchaseOrderDetail> {
}

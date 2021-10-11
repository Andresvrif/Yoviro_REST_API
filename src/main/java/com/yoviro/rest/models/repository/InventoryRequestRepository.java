package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.InventoryRequest;
import com.yoviro.rest.models.repository.projections.SummaryListInventoryRequestNurseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRequestRepository extends PagingAndSortingRepository<InventoryRequest, Long> {
    Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserNameWithCreateAtDesc(Pageable pageable, String userName);

    Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserNameWithCreateAtAsc(Pageable pageable, String userName);


}
package com.yoviro.rest.models.repository;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import com.yoviro.rest.config.enums.StatusProposalEnum;
import com.yoviro.rest.models.entity.InventoryRequest;
import com.yoviro.rest.models.entity.Product;
import com.yoviro.rest.models.repository.projections.SummaryListInventoryRequestNurseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InventoryRequestRepository extends PagingAndSortingRepository<InventoryRequest, Long>, JpaSpecificationExecutor<InventoryRequest> {
    Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserNameWithCreateAtDesc(Pageable pageable, String userName);

    Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserNameWithCreateAtAsc(Pageable pageable, String userName);

    Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserNameAndLikeInventoryRequestNumberWithCreateAtDesc(Pageable pageable, String userName, String search);

    Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserNameAndLikeInventoryRequestNumberWithCreateAtAsc(Pageable pageable, String userName, String search);

    InventoryRequest findInventoryRequestByInventoryRequestNumber(String inventoryRequestNumber);

    List<InventoryRequest> findInventoryRequestByInventoryRequestNumberInAndStatus(List<String> inventoryRequestNumbers, InventoryRequestStatusEnum status);
    List<InventoryRequest> findInventoryRequestByInventoryRequestNumberIn(List<String> inventoryRequestNumbers);

    @Transactional
    @Modifying
    void deleteAllByInventoryRequestNumberIn(List<String> inventoryRequest);
}
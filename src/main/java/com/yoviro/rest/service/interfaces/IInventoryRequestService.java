package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.models.entity.InventoryRequest;
import com.yoviro.rest.models.repository.projections.SummaryListInventoryRequestNurseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IInventoryRequestService {
    Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserName(Pageable pageable,
                                                                                String userName,
                                                                                Boolean ascendant); //Bring All pageable
}

package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.InventoryRequestDetailDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.models.entity.InventoryRequest;
import com.yoviro.rest.models.repository.projections.SummaryListInventoryRequestNurseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IInventoryRequestService {
    Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserName(Pageable pageable,
                                                                                String userName,
                                                                                Boolean ascendant); //Bring All pageable

    @Transactional
    InventoryRequest createOrUpdate(String userName,
                                    String inventoryRequestNumber,
                                    OfficialIdDTO officialIdDTO,
                                    List<InventoryRequestDetailDTO> detailDTOS);
}

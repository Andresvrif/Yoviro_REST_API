package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.InventoryRequestDTO;
import com.yoviro.rest.dto.InventoryRequestDetailDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.search.SearchInventoryRequestDTO;
import com.yoviro.rest.dto.search.SearchProductDTO;
import com.yoviro.rest.dto.search.SearchProposalDTO;
import com.yoviro.rest.models.entity.InventoryRequest;
import com.yoviro.rest.models.repository.projections.SummaryListInventoryRequestNurseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface IInventoryRequestService {
    Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserName(String search,
                                                                                Pageable pageable,
                                                                                String userName,
                                                                                Boolean ascendant); //Bring All pageable

    @Transactional
    InventoryRequest createOrUpdate(String userName,
                                    String inventoryRequestNumber,
                                    OfficialIdDTO officialIdDTO,
                                    List<InventoryRequestDetailDTO> detailDTOS);

    void deleteAllByInventoryRequestNumberIn(List<String> inventoryRequest);

    InventoryRequest findInventoryRequestByReqNumber(String inventoryReqNumber);

    Page<InventoryRequest> search(Pageable pageable, SearchInventoryRequestDTO criteria);
    Page<InventoryRequest> search(Pageable pageable,
                                  SearchInventoryRequestDTO inventoryRequestCriteria,
                                  SearchProposalDTO proposalCriteria);

    void dispatches(List<InventoryRequestDTO> requests);
}

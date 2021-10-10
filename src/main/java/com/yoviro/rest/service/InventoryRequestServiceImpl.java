package com.yoviro.rest.service;

import com.yoviro.rest.models.repository.InventoryRequestRepository;
import com.yoviro.rest.models.repository.UserRepository;
import com.yoviro.rest.models.repository.projections.SummaryListInventoryRequestNurseProjection;
import com.yoviro.rest.service.interfaces.IInventoryRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InventoryRequestServiceImpl implements IInventoryRequestService {
    @Autowired
    private InventoryRequestRepository inventoryRequestRepository;

    @Override
    public Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserName(Pageable pageable,
                                                                                       String userName,
                                                                                       Boolean ascendant) {
        return ascendant? inventoryRequestRepository.summaryListByNurseUserNameWithCreateAtAsc(pageable, userName) :
                          inventoryRequestRepository.summaryListByNurseUserNameWithCreateAtDesc(pageable, userName);
    }
}
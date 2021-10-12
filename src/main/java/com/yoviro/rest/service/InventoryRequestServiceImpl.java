package com.yoviro.rest.service;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import com.yoviro.rest.dto.InventoryRequestDetailDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.InventoryRequestDetailRepository;
import com.yoviro.rest.models.repository.InventoryRequestRepository;
import com.yoviro.rest.models.repository.UserRepository;
import com.yoviro.rest.models.repository.projections.SummaryListInventoryRequestNurseProjection;
import com.yoviro.rest.service.interfaces.IInventoryRequestService;
import com.yoviro.rest.service.interfaces.IProductService;
import com.yoviro.rest.service.interfaces.IResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryRequestServiceImpl implements IInventoryRequestService {
    @Autowired
    private InventoryRequestRepository inventoryRequestRepository;

    @Autowired
    private InventoryRequestDetailRepository inventoryRequestDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IResidentService residentService;

    @Autowired
    private IProductService productService;

    @Override
    public Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserName(Pageable pageable,
                                                                                       String userName,
                                                                                       Boolean ascendant) {
        return ascendant ? inventoryRequestRepository.summaryListByNurseUserNameWithCreateAtAsc(pageable, userName) :
                inventoryRequestRepository.summaryListByNurseUserNameWithCreateAtDesc(pageable, userName);
    }

    @Transactional
    @Override
    public InventoryRequest createOrUpdate(String userName,
                                           String inventoryRequestNumber,
                                           OfficialIdDTO officialIdDTO,
                                           List<InventoryRequestDetailDTO> detailDTOS) {
        InventoryRequest inventoryRequest = inventoryRequestRepository.findInventoryRequestByInventoryRequestNumber(inventoryRequestNumber);

        //Stablish Resident
        Resident resident = residentService.findByOfficialID(officialIdDTO);
        if (resident == null)
            throw new ResponseStatusException(HttpStatus.CHECKPOINT, "Resident not found by Official Ids");

        if (inventoryRequest == null) {
            //We're talking about a new instance
            User user = userRepository.findByUsername(userName);
            Worker worker = user.getWorker();
            if (worker == null)
                throw new ResponseStatusException(HttpStatus.CHECKPOINT, "User not found by UserName");

            inventoryRequest = new InventoryRequest();
            inventoryRequest.setAuthor(worker);
            //Stablish inventory request detail
            List<InventoryRequestDetail> details = defineDetails(detailDTOS);
            for (InventoryRequestDetail detail : details) detail.setInventoryRequest(inventoryRequest);
            inventoryRequest.getDetails().addAll(details);
            inventoryRequest.setResident(resident);

            return inventoryRequestRepository.save(inventoryRequest);
        } else {
            //We're talking about an update
            if (inventoryRequest.getStatus() != InventoryRequestStatusEnum.PENDING)
                throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Inventory request can't be update, because his status is : " + inventoryRequest.getStatus());

            inventoryRequest.getDetails().clear();
            inventoryRequest = inventoryRequestRepository.save(inventoryRequest);
            List<InventoryRequestDetail> details = defineDetails(detailDTOS);
            for (InventoryRequestDetail detail : details) detail.setInventoryRequest(inventoryRequest);
            inventoryRequest.getDetails().addAll(details);
            inventoryRequest.setResident(resident);

            return inventoryRequestRepository.save(inventoryRequest);
        }
    }

    private List<InventoryRequestDetail> defineDetails(List<InventoryRequestDetailDTO> detailDTOS) {
        List<InventoryRequestDetail> details = new ArrayList<>();
        List<String> skus = detailDTOS.stream().map(e -> e.getProductDTO().getSku()).collect(Collectors.toList());
        List<Product> products = productService.findAllBySkuIn(skus);
        if (products.isEmpty())
            throw new ResponseStatusException(HttpStatus.CHECKPOINT, "Products not found");

        Product productToJoin = null;
        for (InventoryRequestDetailDTO detailDTO : detailDTOS) {
            productToJoin = products.stream().filter(e -> e.getSku().compareToIgnoreCase(detailDTO.getProductDTO().getSku()) == 0).findFirst().get();
            InventoryRequestDetail detail = new InventoryRequestDetail();
            detail.setProduct(productToJoin);
            detail.setQuantity(detailDTO.getQuantity());

            details.add(detail);
        }

        return details;
    }
}
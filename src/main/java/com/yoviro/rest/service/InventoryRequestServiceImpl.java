package com.yoviro.rest.service;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import com.yoviro.rest.dto.InventoryRequestDetailDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.search.SearchInventoryRequestDTO;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.InventoryRequestDetailRepository;
import com.yoviro.rest.models.repository.InventoryRequestRepository;
import com.yoviro.rest.models.repository.UserRepository;
import com.yoviro.rest.models.repository.projections.SummaryListInventoryRequestNurseProjection;
import com.yoviro.rest.models.repository.specification.handler.OperatorEnum;
import com.yoviro.rest.models.repository.specification.handler.SearchFilter;
import com.yoviro.rest.models.repository.specification.handler.SearchQuery;
import com.yoviro.rest.models.repository.specification.handler.SpecificationUtil;
import com.yoviro.rest.service.interfaces.IInventoryRequestService;
import com.yoviro.rest.service.interfaces.IProductService;
import com.yoviro.rest.service.interfaces.IResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
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
    public Page<SummaryListInventoryRequestNurseProjection> summaryListByNurseUserName(String search,
                                                                                       Pageable pageable,
                                                                                       String userName,
                                                                                       Boolean ascendant) {
        if (search != null) {
            search = "%".concat(search).concat("%");
            return ascendant ? inventoryRequestRepository.summaryListByNurseUserNameAndLikeInventoryRequestNumberWithCreateAtAsc(pageable, userName, search) :
                    inventoryRequestRepository.summaryListByNurseUserNameAndLikeInventoryRequestNumberWithCreateAtDesc(pageable, userName, search);
        } else {
            return ascendant ? inventoryRequestRepository.summaryListByNurseUserNameWithCreateAtAsc(pageable, userName) :
                    inventoryRequestRepository.summaryListByNurseUserNameWithCreateAtDesc(pageable, userName);
        }
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
                throw new ResponseStatusException(HttpStatus.CHECKPOINT, "Worker not found by UserName");

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

            //TODO refactorizars
            inventoryRequest.getDetails().clear();
            inventoryRequest = inventoryRequestRepository.save(inventoryRequest);
            List<InventoryRequestDetail> details = defineDetails(detailDTOS);
            for (InventoryRequestDetail detail : details) detail.setInventoryRequest(inventoryRequest);
            inventoryRequest.getDetails().addAll(details);
            inventoryRequest.setResident(resident);

            return inventoryRequestRepository.save(inventoryRequest);
        }
    }

    @Override
    public void deleteAllByInventoryRequestNumberIn(List<String> inventoryRequest) {
        inventoryRequestRepository.deleteAllByInventoryRequestNumberIn(inventoryRequest);
    }

    @Override
    public InventoryRequest findInventoryRequestByReqNumber(String inventoryReqNumber) {
        return inventoryRequestRepository.findInventoryRequestByInventoryRequestNumber(inventoryReqNumber);
    }

    @Override
    public Page<InventoryRequest> search(Pageable pageable, SearchInventoryRequestDTO criteria) {
        SearchQuery qry = new SearchQuery();
        List<SearchFilter> inventoryReqCriteria = instanceInventoryReqCriteria(criteria); //Proposal Filter
        qry.setSearchFilter(inventoryReqCriteria);

        Specification<InventoryRequest> specification = SpecificationUtil.bySearchQuery(qry, InventoryRequest.class, Boolean.FALSE);
        return inventoryRequestRepository.findAll(specification, pageable);
    }

    static List<SearchFilter> instanceInventoryReqCriteria(SearchInventoryRequestDTO criteria) {
        List<SearchFilter> filters = new ArrayList<>();
        SearchFilter productFilter;
        if (criteria.getInventoryRequestNumber() != null) {
            productFilter = new SearchFilter();
            productFilter.setProperty("inventoryRequestNumber");
            productFilter.setValue(criteria.getInventoryRequestNumber());
            productFilter.setOperator(OperatorEnum.LIKE);

            filters.add(productFilter);
        }

        if (criteria.getStatus() != null) {
            productFilter = new SearchFilter();
            productFilter.setProperty("status");
            productFilter.setValue(criteria.getStatus());
            productFilter.setOperator(OperatorEnum.EQUALS);

            filters.add(productFilter);
        }

        if (criteria.getStartDate() != null) {
            productFilter = new SearchFilter();
            productFilter.setProperty("createAt");
            productFilter.setValue(criteria.getStartDate().atStartOfDay().minusSeconds(1));
            productFilter.setOperator(OperatorEnum.GREATER_THAN);

            filters.add(productFilter);
        }

        if (criteria.getEndDate() != null) {
            productFilter = new SearchFilter();
            productFilter.setProperty("createAt");
            productFilter.setValue(criteria.getEndDate().plusDays(1).atStartOfDay());
            productFilter.setOperator(OperatorEnum.LESS_THAN);

            filters.add(productFilter);
        }

        return filters;
    }


    private List<InventoryRequestDetail> defineDetails(List<InventoryRequestDetailDTO> detailDTOS) {
        List<InventoryRequestDetail> details = new ArrayList<>();
        List<String> skus = detailDTOS.stream().map(e -> e.getProduct().getSku()).collect(Collectors.toList());
        List<Product> products = productService.findAllBySkuIn(skus);
        if (products.isEmpty())
            throw new ResponseStatusException(HttpStatus.CHECKPOINT, "Products not found");

        Product productToJoin = null;
        for (InventoryRequestDetailDTO detailDTO : detailDTOS) {
            productToJoin = products.stream().filter(e -> e.getSku().compareToIgnoreCase(detailDTO.getProduct().getSku()) == 0).findFirst().get();
            InventoryRequestDetail detail = new InventoryRequestDetail();
            detail.setProduct(productToJoin);
            detail.setQuantity(detailDTO.getQuantity());

            details.add(detail);
        }

        return details;
    }
}
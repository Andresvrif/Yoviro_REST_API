package com.yoviro.rest.service;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import com.yoviro.rest.config.enums.PurcharseOrderEnum;
import com.yoviro.rest.config.enums.StatusProposalEnum;
import com.yoviro.rest.dto.ProposalDTO;
import com.yoviro.rest.dto.PurchaseOrderDTO;
import com.yoviro.rest.dto.search.SearchPurchaseOrderDTO;
import com.yoviro.rest.handler.ProposalHandler;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.ProposalRepository;
import com.yoviro.rest.models.repository.PurchaseOrderRepository;
import com.yoviro.rest.models.repository.UserRepository;
import com.yoviro.rest.models.repository.specification.handler.*;
import com.yoviro.rest.service.interfaces.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<PurchaseOrder> search(Pageable pageable,
                                      SearchPurchaseOrderDTO criteria) {
        //Define Search Criteria
        SearchQuery qry = new SearchQuery();
        List<SearchFilter> purchaseOrderCriteria = instancePurchaseOrderCriteria(criteria);
        List<SearchFilter> proposalCriteria = ProposalServiceImpl.instanceProposalCriteria(criteria.getProposalCriteria());

        JoinColumnProps joinColumnProposal = new JoinColumnProps();
        joinColumnProposal.setJoinColumnName("proposals");
        joinColumnProposal.setSearchFilter(proposalCriteria);

        //Add filters & joins
        qry.addJoinColumnProp(joinColumnProposal);
        qry.setSearchFilter(purchaseOrderCriteria);

        Specification<PurchaseOrder> specification = SpecificationUtil.bySearchQuery(qry, PurchaseOrder.class, Boolean.TRUE);
        return purchaseOrderRepository.findAll(specification, pageable);
    }

    @Override
    public PurchaseOrder findByPurcharseOrderNumber(String purchaseOrderNumber) {
        return purchaseOrderRepository.findByPurchaseOrderNumber(purchaseOrderNumber);
    }

    @Transactional
    @Override
    public Proposal updateReferencesNumbers(String userName,
                                            ProposalDTO proposalDTO) {
        List<String> purchaseOrderNumbers = proposalDTO.getPurchaseOrders().stream().map(e -> e.getPurchaseOrderNumber()).collect(Collectors.toList());
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAllByPurchaseOrderNumberIn(purchaseOrderNumbers);

        //Update status, refNumber
        Optional<PurchaseOrderDTO> optionalPurchaseOrderDTO = null;
        PurchaseOrderDTO purchaseOrderDTO = null;
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            optionalPurchaseOrderDTO = proposalDTO.getPurchaseOrders().stream().filter(e -> e.getPurchaseOrderNumber().trim().compareToIgnoreCase(purchaseOrder.getPurchaseOrderNumber()) == 0).findFirst();
            if (optionalPurchaseOrderDTO.isEmpty()) continue; //It doesn't find, so continue with the next one
            purchaseOrderDTO = optionalPurchaseOrderDTO.get();

            //Business flow
            if (purchaseOrder.getStatus() == PurcharseOrderEnum.CANCELLED) continue; //If it's cancelled is untouchable
            Boolean isACancellation = purchaseOrderDTO.getReasonForDenied() != null;
            if (isACancellation) {
                purchaseOrder.setStatus(PurcharseOrderEnum.CANCELLED);
                purchaseOrder.setReasonForDenied(purchaseOrderDTO.getReasonForDenied());
            } else {
                //Flow of change the status from QUOTED to BOUGHT
                purchaseOrder.setReferenceNumber(purchaseOrderDTO.getReferenceNumber());
                purchaseOrder.setStatus(PurcharseOrderEnum.BOUGHT);
            }
        }

        //TODO check if there is a cancellation inside
        //FLOW COPY PROPOSAL
        purchaseOrderRepository.saveAll(purchaseOrders);
        Boolean hasAPurchaseOrderCancelled = purchaseOrders.stream().anyMatch(e -> e.getStatus() == PurcharseOrderEnum.CANCELLED);
        if (hasAPurchaseOrderCancelled) {
            Proposal src = proposalRepository.findProposalsByProposalNumber(proposalDTO.getProposalNumber());
            Proposal dest = new Proposal();
            dest.setStatus(StatusProposalEnum.PENDING);
            dest.setAuthor(bringStoreKeeperFromUserName(userName));
            ProposalHandler.transferCleanDetails(src, dest);

            //We cancel the old proposal
            src.setStatus(StatusProposalEnum.CANCELED);
            proposalRepository.save(src);
            return proposalRepository.save(dest);
        } else {
            return null;
        }
    }

    private StoreKeeper bringStoreKeeperFromUserName(String userName) {
        User user = userRepository.findByUsername(userName);
        Worker worker = user.getWorker();
        if (worker == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worker not found by UserName");

        if (!(worker instanceof StoreKeeper))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The worker has to be a store keeper");

        return (StoreKeeper) worker;
    }

    static List<SearchFilter> instancePurchaseOrderCriteria(SearchPurchaseOrderDTO criteria) {
        List<SearchFilter> filters = new ArrayList<>();
        SearchFilter purchaseOrderFilter;
        if (criteria.getStatus() != null) {
            purchaseOrderFilter = new SearchFilter();
            purchaseOrderFilter.setProperty("status");
            purchaseOrderFilter.setValue(criteria.getStatus());
            purchaseOrderFilter.setOperator(OperatorEnum.EQUALS);

            filters.add(purchaseOrderFilter);
        }

        if (criteria.getPurchaseOrderNumber() != null) {
            purchaseOrderFilter = new SearchFilter();
            purchaseOrderFilter.setProperty("purchaseOrderNumber");
            purchaseOrderFilter.setValue(criteria.getPurchaseOrderNumber());
            purchaseOrderFilter.setOperator(OperatorEnum.LIKE);

            filters.add(purchaseOrderFilter);
        }

        if (criteria.getReferenceNumber() != null) {
            purchaseOrderFilter = new SearchFilter();
            purchaseOrderFilter.setProperty("referenceNumber");
            purchaseOrderFilter.setValue(criteria.getReferenceNumber());
            purchaseOrderFilter.setOperator(OperatorEnum.LIKE);

            filters.add(purchaseOrderFilter);
        }

        return filters;
    }
}

package com.yoviro.rest.service;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import com.yoviro.rest.config.enums.PurcharseOrderEnum;
import com.yoviro.rest.config.enums.StatusProposalEnum;
import com.yoviro.rest.dto.InventoryRequestDTO;
import com.yoviro.rest.dto.ProposalDTO;
import com.yoviro.rest.dto.PurchaseOrderDTO;
import com.yoviro.rest.dto.PurchaseOrderDetailDTO;
import com.yoviro.rest.dto.search.SearchProposalDTO;
import com.yoviro.rest.handler.ProposalHandler;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.*;
import com.yoviro.rest.models.repository.specification.handler.OperatorEnum;
import com.yoviro.rest.models.repository.specification.handler.SearchFilter;
import com.yoviro.rest.models.repository.specification.handler.SearchQuery;
import com.yoviro.rest.models.repository.specification.handler.SpecificationUtil;
import com.yoviro.rest.service.interfaces.IContactService;
import com.yoviro.rest.service.interfaces.IProposalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProposalServiceImpl implements IProposalService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Autowired
    private InventoryRequestRepository inventoryRequestRepository;

    @Autowired
    private IContactService contactService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<Proposal> search(Pageable pageable,
                                 SearchProposalDTO criteria) {
        SearchQuery qry = new SearchQuery();
        List<SearchFilter> productCriteria = instanceProposalCriteria(criteria); //Proposal Filter
        qry.setSearchFilter(productCriteria);

        Specification<Proposal> specification = SpecificationUtil.bySearchQuery(qry, Proposal.class, Boolean.FALSE);
        return proposalRepository.findAll(specification, pageable);
    }

    @Transactional
    @Override
    public Proposal createOrUpdate(String userName,
                                   ProposalDTO proposalDTO) throws Exception {
        return proposalDTO.getProposalNumber() == null ? createProposal(userName, proposalDTO) : updateProposal(userName, proposalDTO);
    }

    @Override
    public Proposal findProposalByProposalNumber(String proposalNumber) {
        return proposalRepository.findProposalsByProposalNumber(proposalNumber);
    }

    @Transactional
    @Override
    public Proposal updateStatus(String userName,
                                 ProposalDTO proposalDTO) {
        User user = userRepository.findByUsername(userName);
        Worker worker = user.getWorker();
        if (worker == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worker not found by UserName");

        if (!(worker instanceof Director))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The worker has to be a director");

        Proposal proposal = proposalRepository.findProposalsByProposalNumber(proposalDTO.getProposalNumber());
        if (proposal == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Isn't exist a proposal with number : " + proposalDTO.getProposalNumber());

        if (proposal.getStatus() == StatusProposalEnum.REJECTED || proposal.getStatus() == StatusProposalEnum.CANCELED)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can evaluate a rejected or canceled proposal");

        //Update Details
        //Update Inventory request
        Optional<InventoryRequest> optionalInventoryRequest;
        InventoryRequest inventoryRequestToBeUpdated;
        for (InventoryRequestDTO inventoryRequestDTO : proposalDTO.getInventoryRequests()) {
            optionalInventoryRequest = proposal.getInventoryRequests().stream().filter(e -> e.getInventoryRequestNumber().compareToIgnoreCase(inventoryRequestDTO.getInventoryRequestNumber()) == 0).findFirst();

            if (optionalInventoryRequest.isEmpty()) continue; //TODO check if the system should thrown an exception
            inventoryRequestToBeUpdated = optionalInventoryRequest.get();

            inventoryRequestToBeUpdated.setStatus(inventoryRequestDTO.getStatus());
            if (inventoryRequestDTO.getReasonForDenied() != null) {
                inventoryRequestToBeUpdated.setReasonForDenied(inventoryRequestDTO.getReasonForDenied());
            }
        }

        //Update Purchase order
        Optional<PurchaseOrder> optionalPurchaseOrder;
        PurchaseOrder purchaseOrderToBeUpdated;
        for (PurchaseOrderDTO purchaseOrderDTO : proposalDTO.getPurchaseOrders()) {
            optionalPurchaseOrder = proposal.getPurchaseOrders().stream().filter(e -> e.getPurchaseOrderNumber().compareToIgnoreCase(purchaseOrderDTO.getPurchaseOrderNumber()) == 0).findFirst();
            if (optionalPurchaseOrder.isEmpty()) continue; //TODO check if the system should thrown an exception
            purchaseOrderToBeUpdated = optionalPurchaseOrder.get();

            purchaseOrderToBeUpdated.setStatus(purchaseOrderDTO.getStatus());
            if (purchaseOrderDTO.getReasonForDenied() != null) {
                purchaseOrderToBeUpdated.setReasonForDenied(purchaseOrderDTO.getReasonForDenied());
            }
        }

        //Save all details
        inventoryRequestRepository.saveAll(proposal.getInventoryRequests());
        purchaseOrderRepository.saveAll(proposal.getPurchaseOrders());

        //Check if there is a cancelled element
        Boolean isThereAInvReqRejected = proposal.getInventoryRequests().stream().anyMatch(e -> e.getStatus() == InventoryRequestStatusEnum.REJECTED);
        Boolean isThereAPurchaseRejected = proposal.getPurchaseOrders().stream().anyMatch(e -> e.getStatus() == PurcharseOrderEnum.CANCELLED || e.getStatus() == PurcharseOrderEnum.REJECTED);

        //If there isn't cancelled details, return the same proposal
        if (!isThereAInvReqRejected && !isThereAPurchaseRejected) return proposal;

        //It has a rejected or cancelled
        proposal.setStatus(StatusProposalEnum.REJECTED);
        proposal = proposalRepository.save(proposal);

        //Copy it
        Proposal newProposal = new Proposal();
        newProposal.setStatus(StatusProposalEnum.PENDING);
        newProposal.setAuthor(proposal.getAuthor());

        ProposalHandler.transferCleanDetails(proposal, newProposal);
        return proposalRepository.save(newProposal);
    }


    private Proposal updateProposal(String userName,
                                    ProposalDTO proposalDTO) throws Exception {
        Proposal proposal = proposalRepository.findProposalsByProposalNumber(proposalDTO.getProposalNumber());
        if (proposal == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Isn't exist a proposal with number : " + proposalDTO.getProposalNumber());

        if (proposal.getStatus() != StatusProposalEnum.PENDING)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't update a proposal with a status : " + proposal.getStatus());

        //Update Inventory Request
        handleUpdateInventoryRequest(proposal, proposalDTO);
        handleUpdatePurchaseOrders(proposal, proposalDTO);

        return proposalRepository.save(proposal);
    }

    /**
     * Author: Andrés V.
     * Desc : Handles update creation of inventory request like remove old items, add news or keep items to be updated
     *
     * @param proposal
     * @param proposalDTO
     */
    private void handleUpdateInventoryRequest(Proposal proposal,
                                              ProposalDTO proposalDTO) {
        //Clean relationship
        List<String> inventoryRequestNumbers = proposalDTO.getInventoryRequests().stream().map(e -> e.getInventoryRequestNumber()).collect(Collectors.toList());
        List<InventoryRequest> toBeKeeped = proposal.getInventoryRequests().stream().filter(e -> inventoryRequestNumbers.contains(e.getInventoryRequestNumber())).collect(Collectors.toList());
        List<InventoryRequest> toBeRemoved = proposal.getInventoryRequests().stream().filter(e -> !toBeKeeped.contains(e)).collect(Collectors.toList());
        List<String> inventoryRequestNumberToBeAdded = inventoryRequestNumbers.stream().filter(e -> !toBeKeeped.stream().anyMatch(e1 -> e1.getInventoryRequestNumber().compareToIgnoreCase(e) == 0)).collect(Collectors.toList());
        if (!inventoryRequestNumberToBeAdded.isEmpty()) {
            List<InventoryRequest> inventoryRequestsToBeAdded = bringAvailableInventoryRequests(inventoryRequestNumberToBeAdded);
            addInventoryRequestForProposal(proposal, inventoryRequestsToBeAdded); //Remove add new items
        }

        removeInventoryRequestFromProposal(proposal, toBeRemoved); //Remove old items
    }

    private void handleUpdatePurchaseOrders(Proposal proposal,
                                            ProposalDTO proposalDTO) throws Exception {
        //Define Trxs
        List<String> purchaseOrderNumbers = proposalDTO.getPurchaseOrders().stream().filter(e -> e.getPurchaseOrderNumber() != null).map(e -> e.getPurchaseOrderNumber()).collect(Collectors.toList());
        List<PurchaseOrder> toBeKeeped = proposal.getPurchaseOrders().stream().filter(e -> purchaseOrderNumbers.contains(e.getPurchaseOrderNumber())).collect(Collectors.toList());
        List<PurchaseOrder> toBeRemoved = proposal.getPurchaseOrders().stream().filter(e -> !toBeKeeped.contains(e)).collect(Collectors.toList());
        List<PurchaseOrderDTO> purchaseOrderToBeAdded = proposalDTO.getPurchaseOrders().stream().filter(e -> e.getPurchaseOrderNumber() == null).collect(Collectors.toList());

        //Update Flow
        updatePurchaseOrdersFromProposal(toBeKeeped, proposalDTO.getPurchaseOrders());

        //Add FLow
        if (!purchaseOrderToBeAdded.isEmpty()) {
            List<PurchaseOrder> purchaseOrders = mapNewPurchaseOrder((StoreKeeper) proposal.getAuthor(), purchaseOrderToBeAdded);
            //Save purchase orders
            purchaseOrders = (List<PurchaseOrder>) purchaseOrderRepository.saveAll(purchaseOrders);
            proposal.getPurchaseOrders().addAll(purchaseOrders);
        }

        removePurchaseOrderFromProposal(proposal, toBeRemoved); //Remove old items
    }

    private void updatePurchaseOrdersFromProposal(List<PurchaseOrder> purchaseOrdersToBeUpdate,
                                                  List<PurchaseOrderDTO> purchaseOrdersInfo) {
        PurchaseOrderDTO purchaseOrderDTOInfo;
        for (PurchaseOrder toBeUpdate : purchaseOrdersToBeUpdate) {
            purchaseOrderDTOInfo = purchaseOrdersInfo.stream().filter(e -> e.getPurchaseOrderNumber().compareToIgnoreCase(toBeUpdate.getPurchaseOrderNumber()) == 0).findFirst().get();
            if (purchaseOrderDTOInfo == null) continue;

            //Update Purchase Order
            updatePurchaseOrderDetail(toBeUpdate, purchaseOrderDTOInfo);
        }
    }

    private void updatePurchaseOrderDetail(PurchaseOrder purchaseOrder,
                                           PurchaseOrderDTO purchaseOrderDTO) {
        //Define Trxs
        List<String> purchaseOrderDetailRefs = purchaseOrderDTO.getPurchaseOrderDetails().stream().map(e -> e.getProduct().getSku()).collect(Collectors.toList());
        List<PurchaseOrderDetail> toBeKeeped = purchaseOrder.getPurcharseOrderDetails().stream().filter(e -> purchaseOrderDetailRefs.contains(e.getProduct().getSku())).collect(Collectors.toList());
        List<PurchaseOrderDetail> toBeRemoved = purchaseOrder.getPurcharseOrderDetails().stream().filter(e -> !toBeKeeped.contains(e)).collect(Collectors.toList());

        //Define items to be added
        List<String> notConsider1 = toBeKeeped.stream().map(e -> e.getProduct().getSku()).collect(Collectors.toList());
        List<String> notConsider2 = toBeRemoved.stream().map(e -> e.getProduct().getSku()).collect(Collectors.toList());
        notConsider1.addAll(notConsider2);
        HashSet<String> notConsider = new HashSet<String>(notConsider1);
        List<PurchaseOrderDetailDTO> toBeAdded = purchaseOrderDTO.getPurchaseOrderDetails().stream().filter(e -> !notConsider.contains(e.getProduct().getSku())).collect(Collectors.toList());

        //Adds
        Product product;
        PurchaseOrderDetail newDetail;
        for (PurchaseOrderDetailDTO purchaseOrderDetailDTO : toBeAdded) {
            product = productRepository.findBySku(purchaseOrderDetailDTO.getProduct().getSku());

            newDetail = new PurchaseOrderDetail();
            newDetail.setProduct(product);
            newDetail.setQuantity(purchaseOrderDetailDTO.getQuantity());
            newDetail.setPurchaseOrder(purchaseOrder);

            purchaseOrder.getPurcharseOrderDetails().add(newDetail);
        }

        //Updates
        PurchaseOrderDetailDTO detailInfoUpdate;
        for (PurchaseOrderDetail purchaseOrderDetail : toBeKeeped) {
            detailInfoUpdate = purchaseOrderDTO.getPurchaseOrderDetails().stream().filter(e -> e.getProduct().getSku().compareToIgnoreCase(purchaseOrderDetail.getProduct().getSku()) == 0).findFirst().get();
            if (detailInfoUpdate == null) continue;
            purchaseOrderDetail.setQuantity(detailInfoUpdate.getQuantity());
        }

        //Remove
        toBeRemoved.forEach(e -> {
            purchaseOrder.getPurcharseOrderDetails().remove(e);
            purchaseOrderDetailRepository.delete(e);
        });
    }

    private void removePurchaseOrderFromProposal(Proposal proposal,
                                                 List<PurchaseOrder> toBeDeleted) {
        if (toBeDeleted.isEmpty()) return;

        //Remove proposal from inventory requests
        toBeDeleted.forEach(e -> e.getProposals().remove(proposal)); //Break relantionship with PROPOSAL

        proposal.getPurchaseOrders().removeAll(toBeDeleted);
        purchaseOrderRepository.saveAll(toBeDeleted);

        //If there isn't a proposal related with the purchase, remove it
        toBeDeleted.forEach(e -> {
            if (e.getProposals().isEmpty()) {
                purchaseOrderRepository.delete(e);
            }
        });
    }

    private void addInventoryRequestForProposal(Proposal proposal,
                                                List<InventoryRequest> toBeAdded) {
        toBeAdded.forEach(e -> {
            if (e.getStatus() != InventoryRequestStatusEnum.PENDING)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't assign the inventory request : " + e.getInventoryRequestNumber() + ", because is in " + e.getStatus() + " status");
            e.setStatus(InventoryRequestStatusEnum.IN_PROGRESS);
        });
        proposal.getInventoryRequests().addAll(toBeAdded);
    }

    private void removeInventoryRequestFromProposal(Proposal proposal,
                                                    List<InventoryRequest> toBeDeleted) {
        if (toBeDeleted.isEmpty()) return;

        //Remove proposal from inventory requests
        toBeDeleted.forEach(e -> {
            if (e.getStatus() != InventoryRequestStatusEnum.IN_PROGRESS && e.getStatus() != InventoryRequestStatusEnum.PENDING)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove inventory request : " + e.getInventoryRequestNumber() + ", because is in " + e.getStatus() + " status");

            e.getProposals().remove(proposal);
            if (e.getProposals().isEmpty()) e.setStatus(InventoryRequestStatusEnum.PENDING);
        });

        proposal.getInventoryRequests().removeAll(toBeDeleted);
        inventoryRequestRepository.saveAll(toBeDeleted);
    }

    private Proposal createProposal(String userName,
                                    ProposalDTO proposalDTO) throws Exception {
        Proposal proposal = null;
        User user = userRepository.findByUsername(userName);
        Worker worker = user.getWorker();
        if (worker == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worker not found by UserName");

        if (!(worker instanceof StoreKeeper))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The worker has to be a store keeper");

        List<String> inventoryRequestNumbers = proposalDTO.getInventoryRequests().stream().map(e -> e.getInventoryRequestNumber()).collect(Collectors.toList());
        List<InventoryRequest> inventoryRequests = bringAvailableInventoryRequests(inventoryRequestNumbers);
        List<PurchaseOrder> purchaseOrders = mapNewPurchaseOrder((StoreKeeper) worker, proposalDTO.getPurchaseOrders());

        //We update the status in inventory request related to proposals
        inventoryRequests.forEach(e -> e.setStatus(InventoryRequestStatusEnum.IN_PROGRESS));
        inventoryRequests = (List<InventoryRequest>) inventoryRequestRepository.saveAll(inventoryRequests);

        //Save purchase orders
        purchaseOrders = (List<PurchaseOrder>) purchaseOrderRepository.saveAll(purchaseOrders);

        //Create Proposal
        proposal = new Proposal();
        proposal.setAuthor((StoreKeeper) worker);
        proposal.setStatus(StatusProposalEnum.PENDING);
        proposal.setInventoryRequests(inventoryRequests);
        proposal.setPurchaseOrders(purchaseOrders);

        //Save it
        return proposalRepository.save(proposal);
    }

    private List<InventoryRequest> bringAvailableInventoryRequests(List<String> inventoryRequestNumbers) {
        List<InventoryRequest> inventoryRequests = inventoryRequestRepository.findInventoryRequestByInventoryRequestNumberInAndStatus(inventoryRequestNumbers, InventoryRequestStatusEnum.PENDING);
        if (inventoryRequests.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There isn't inventory request with a PENDING status");

        return inventoryRequests;
    }

    private List<PurchaseOrder> mapNewPurchaseOrder(StoreKeeper author,
                                                    List<PurchaseOrderDTO> purchaseOrderDTOS) throws Exception {
        Contact contact = null;
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        PurchaseOrder purchaseOrder = null;
        PurchaseOrderDetail purchaseOrderDetail = null;
        List<PurchaseOrderDetail> details = new ArrayList<>();
        Product product = null;
        for (PurchaseOrderDTO purchaseOrderDTO : purchaseOrderDTOS) {
            purchaseOrder = new PurchaseOrder();
            purchaseOrder.setAuthor(author);
            purchaseOrder.setStatus(PurcharseOrderEnum.QUOTED);

            //Map Provider
            Company company = (Company) contactService.getOrCreateContact(purchaseOrderDTO.getCompany());
            purchaseOrder.setCompany(company);
            purchaseOrder.setTotalPrice(purchaseOrderDTO.getTotalPrice());

            //Map Detail
            details = new ArrayList<>();
            for (PurchaseOrderDetailDTO detailDTO : purchaseOrderDTO.getPurchaseOrderDetails()) {
                product = productRepository.findBySku(detailDTO.getProduct().getSku());
                purchaseOrderDetail = new PurchaseOrderDetail();
                purchaseOrderDetail.setProduct(product);
                purchaseOrderDetail.setQuantity(detailDTO.getQuantity());
                purchaseOrderDetail.setPurchaseOrder(purchaseOrder);

                details.add(purchaseOrderDetail);
            }

            purchaseOrder.setPurcharseOrderDetails(details);
            purchaseOrders.add(purchaseOrder);
        }

        return purchaseOrders;
    }


    static List<SearchFilter> instanceProposalCriteria(SearchProposalDTO criteria) {
        List<SearchFilter> filters = new ArrayList<>();
        SearchFilter proposalFilter;
        if (criteria.getProposalNumber() != null) {
            proposalFilter = new SearchFilter();
            proposalFilter.setProperty("proposalNumber");
            proposalFilter.setValue(criteria.getProposalNumber());
            proposalFilter.setOperator(OperatorEnum.LIKE);

            filters.add(proposalFilter);
        }

        if (criteria.getStatus() != null) {
            proposalFilter = new SearchFilter();
            proposalFilter.setProperty("status");
            proposalFilter.setValue(criteria.getStatus());
            proposalFilter.setOperator(OperatorEnum.EQUALS);

            filters.add(proposalFilter);
        }

        return filters;
    }
}
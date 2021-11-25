package com.yoviro.rest.controller;

import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.config.enums.PurcharseOrderEnum;
import com.yoviro.rest.config.enums.StatusProposalEnum;
import com.yoviro.rest.dto.ProposalDTO;
import com.yoviro.rest.dto.PurchaseOrderDTO;
import com.yoviro.rest.dto.search.SearchProposalDTO;
import com.yoviro.rest.dto.search.SearchPurchaseOrderDTO;
import com.yoviro.rest.handler.PurchaseOrderHandler;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.security.service.IJWTService;
import com.yoviro.rest.service.interfaces.IPurchaseOrderService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/purchaseOrders")
public class PurchaseRestController {

    @Autowired
    private IJWTService jwtService;

    @Autowired
    IPurchaseOrderService purchaseOrderService;

    @GetMapping("/summaryList")
    public Map<String, Object> summaryList(@RequestParam(required = false) String proposalNumber,
                                           @RequestParam(required = false) PurcharseOrderEnum status,
                                           @RequestParam(required = true) String page) {
        //Map Search Criteria
        SearchPurchaseOrderDTO purchaseOrderCriteria = new SearchPurchaseOrderDTO();
        SearchProposalDTO proposalCriteria = new SearchProposalDTO();
        purchaseOrderCriteria.setStatus(status);
        proposalCriteria.setProposalNumber(proposalNumber);

        purchaseOrderCriteria.setProposalCriteria(proposalCriteria);

        //Define Page criteria
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable pageableByProposalNumber = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("createAt").ascending());

        Page<PurchaseOrder> pageResult = purchaseOrderService.search(pageableByProposalNumber, purchaseOrderCriteria);

        //Define Response
        Map<String, Object> response = Map.ofEntries(
                Map.entry(AppConfig.METADATA_TAG, JSONUtil.pageToJson(pageResult)),
                Map.entry("content", wrapSummaryList(pageResult.getContent()))
        );
        return response;
    }

    private ArrayList<Map<String, Object>> wrapSummaryList(List<PurchaseOrder> purchaseOrders) {
        List dataList = new ArrayList<>();
        Map item = null;
        Proposal lastProposalRelated = null;
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            lastProposalRelated = PurchaseOrderHandler.lastProposalFromPurchaseOrder(purchaseOrder);
            item = Map.ofEntries(
                    Map.entry("purchaseOrderNumber", purchaseOrder.getPurchaseOrderNumber()),
                    Map.entry("provider", purchaseOrder.getCompany().getName()),
                    Map.entry("referenceNumber", purchaseOrder.getReferenceNumber() == null ? Optional.empty() : purchaseOrder.getReferenceNumber()),
                    Map.entry("status", purchaseOrder.getStatus()),
                    Map.entry("proposalNumber", lastProposalRelated == null ? Optional.empty() : lastProposalRelated.getProposalNumber()),
                    Map.entry("totalPrice", purchaseOrder.getTotalPrice())
            );

            dataList.add(item);
        }
        return (ArrayList<Map<String, Object>>) dataList;
    }

    @GetMapping("/detail")
    public Map<String, Object> detail(@RequestHeader(name = "Authorization") String authorization,
                                      @RequestParam(required = true) String purchaseOrderNumber) {
        PurchaseOrder purchaseOrder = purchaseOrderService.findByPurcharseOrderNumber(purchaseOrderNumber);
        StoreKeeper storeKeeper = purchaseOrder.getAuthor();
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //Map Proposals related
        List<Map> proposals = wrapProposals(purchaseOrder);

        return Map.ofEntries(
                Map.entry("purchaseOrderNumber", purchaseOrder.getPurchaseOrderNumber()),
                Map.entry("status", purchaseOrder.getStatus()),
                Map.entry("createAt", purchaseOrder.getCreateAt()),
                Map.entry("totalPrice", purchaseOrder.getTotalPrice()),
                Map.entry("provider", purchaseOrder.getCompany().getName()),
                Map.entry("author", storeKeeper.getPerson().getFullName()),
                Map.entry("proposals", proposals)
        );
    }

    private List<Map> wrapProposals(PurchaseOrder purchaseOrder) {
        List<Map> content = new ArrayList<>();
        Map<String, Object> row = null;
        String reasonForDenied = null;
        List<Proposal> orderedProposals = purchaseOrder.getProposals();
        orderedProposals.sort(Comparator.comparing(Proposal::getCreateAt));
        Collections.reverse(orderedProposals);

        for (Proposal proposal : orderedProposals) {
            reasonForDenied = proposal.getReasonForDenied();
            if ((proposal.getStatus() == StatusProposalEnum.REJECTED || proposal.getStatus() == StatusProposalEnum.CANCELED) && reasonForDenied != null) {
                row = Map.ofEntries(
                        Map.entry("proposalNumber", proposal.getProposalNumber()),
                        Map.entry("status", proposal.getStatus()),
                        Map.entry("createAt", proposal.getCreateAt()),
                        Map.entry("reasonForDenied", reasonForDenied)
                );
            } else {
                row = Map.ofEntries(
                        Map.entry("proposalNumber", proposal.getProposalNumber()),
                        Map.entry("status", proposal.getStatus()),
                        Map.entry("createAt", proposal.getCreateAt())
                );
            }
            content.add(row);
        }

        return content;
    }

    @PutMapping("/updateReferences")
    public String updateReferences(@RequestHeader(name = "Authorization") String authorization,
                                   @RequestBody String json) throws JSONException {
        //Retrieve userName from token
        String token = jwtService.retrieveToken(authorization);
        String userName = jwtService.getUserName(token);

        //Map Jsons
        JSONObject jsonProposal = new JSONObject(json);
        JSONArray purchaseOrdersToBeUpdated = jsonProposal.getJSONArray("purchaseOrders");
        JSONObject jsonPurchaseOrder;

        //Map DTOs
        ProposalDTO proposalDTO = new ProposalDTO();
        proposalDTO.setProposalNumber(jsonProposal.getString("proposalNumber"));
        List<PurchaseOrderDTO> purchaseOrderDTOList = new ArrayList<>();
        PurchaseOrderDTO purchaseOrderDTO = null;
        for (int i = 0; i < purchaseOrdersToBeUpdated.length(); i++) {
            jsonPurchaseOrder = purchaseOrdersToBeUpdated.getJSONObject(i);

            //Map Purchase order info
            purchaseOrderDTO = new PurchaseOrderDTO();
            purchaseOrderDTO.setPurchaseOrderNumber(jsonPurchaseOrder.getString("purchaseOrderNumber"));
            if (jsonPurchaseOrder.has("referenceNumber"))
                purchaseOrderDTO.setReferenceNumber(jsonPurchaseOrder.getString("referenceNumber"));
            if (jsonPurchaseOrder.has("reasonForDenied"))
                purchaseOrderDTO.setReasonForDenied(jsonPurchaseOrder.getString("reasonForDenied"));

            purchaseOrderDTOList.add(purchaseOrderDTO);
        }

        proposalDTO.setPurchaseOrders(purchaseOrderDTOList);

        Proposal response = purchaseOrderService.updateReferencesNumbers(userName, proposalDTO);
        if (response != null){
            System.out.println("response.getProposalNumber()-------------> " + response.getProposalNumber());
        }
        return response == null ? null : response.getProposalNumber();
    }
}
package com.yoviro.rest.controller;

import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.config.enums.StatusProposalEnum;
import com.yoviro.rest.dto.*;
import com.yoviro.rest.dto.search.SearchProductDTO;
import com.yoviro.rest.dto.search.SearchProposalDTO;
import com.yoviro.rest.models.entity.InventoryRequest;
import com.yoviro.rest.models.entity.OfficialId;
import com.yoviro.rest.models.entity.Person;
import com.yoviro.rest.models.entity.Proposal;
import com.yoviro.rest.security.service.IJWTService;
import com.yoviro.rest.service.interfaces.IProductService;
import com.yoviro.rest.service.interfaces.IProposalService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import org.javamoney.moneta.Money;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/proposal")
public class ProposalRestController {
    @Autowired
    IProposalService proposalService;

    @Autowired
    private IJWTService jwtService;

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam(required = false) String proposalNumber,
                                      @RequestParam(required = false) StatusProposalEnum status,
                                      @RequestParam(required = true) String page) {
        //Define Search criteria
        SearchProposalDTO criteria = new SearchProposalDTO();
        criteria.setProposalNumber(proposalNumber);
        criteria.setStatus(status);

        //Define Page criteria
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable pageableByProposalNumber = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("proposalNumber").ascending());

        Page<Proposal> pageResult = proposalService.search(pageableByProposalNumber, criteria);
        if (pageResult.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No proposals found");
        }

        //Define Response
        Map<String, Object> response = Map.ofEntries(
                Map.entry(AppConfig.METADATA_TAG, JSONUtil.pageToJson(pageResult)),
                Map.entry("content", wrapSearchResult(pageResult.getContent()))
        );
        return response;
    }

    private ArrayList<Map> wrapSearchResult(List<Proposal> proposals) {
        List dataList = new ArrayList<>();
        Map item = null;
        Person evaluator = null; //Only director can evaluate proposals
        Person storeKeeper = null;
        for (Proposal proposal : proposals) {
            storeKeeper = proposal.getAuthor().getPerson();

            //Evaluate null evaluator
            if (proposal.getEvaluator() != null) {
                evaluator = proposal.getEvaluator().getPerson();
            }

            item = Map.ofEntries(
                    Map.entry("proposalNumber", proposal.getProposalNumber()),
                    Map.entry("status", proposal.getStatus()),
                    Map.entry("storeKeeper", storeKeeper.getFullName()),
                    Map.entry("evaluator", evaluator == null ? Optional.empty() : evaluator.getFullName()),
                    Map.entry("updateTime", proposal.getUpdateAt()),
                    Map.entry("totalPrice", proposal.getTotalPrice().toString())
            );

            dataList.add(item);
        }
        return (ArrayList<Map>) dataList;
    }

    @PostMapping("/createOrUpdate")
    public String newProposal(@RequestHeader(name = "Authorization") String authorization,
                              @RequestBody String json) throws Exception {

        //Retrieve userName from token
        String token = jwtService.retrieveToken(authorization);
        String userName = jwtService.getUserName(token);

        JSONObject jsonObject = new JSONObject(json);

        //DTOs
        List<InventoryRequestDTO> inventoryRequestDTOS = wrapInventoryRequests(jsonObject.getJSONArray("inventoryRequests"));
        List<PurchaseOrderDTO> purchaseOrdersDTOS = wrapPurchaseOrders(jsonObject.getJSONArray("purchaseOrders"));

        //Map Proposal
        ProposalDTO proposalDTO = new ProposalDTO();
        proposalDTO.setInventoryRequests(inventoryRequestDTOS);
        proposalDTO.setPurchaseOrders(purchaseOrdersDTOS);
        if (jsonObject.has("proposalNumber")) {
            proposalDTO.setProposalNumber(jsonObject.getString("proposalNumber"));
        }

        Proposal proposal = proposalService.createOrUpdate(userName, proposalDTO);

        return proposal.getProposalNumber();
    }

    private List<InventoryRequestDTO> wrapInventoryRequests(JSONArray json) throws JSONException {
        ArrayList<InventoryRequestDTO> inventoryRequestDTOS = new ArrayList<>();
        InventoryRequestDTO inventoryRequestDTO = null;
        for (int i = 0; i < json.length(); i++) {
            inventoryRequestDTO = new InventoryRequestDTO();
            inventoryRequestDTO.setInventoryRequestNumber(json.getString(i));

            inventoryRequestDTOS.add(inventoryRequestDTO);
        }

        return inventoryRequestDTOS;
    }

    private List<PurchaseOrderDTO> wrapPurchaseOrders(JSONArray json) throws JSONException {
        ArrayList<PurchaseOrderDTO> purchaseOrderDTOS = new ArrayList<>();
        JSONObject officialIdJson = null;
        JSONObject purcharOrderJson = null;
        PurchaseOrderDTO purchaseOrderDTO = null;
        CompanyDTO companyDTO = null;
        OfficialIdDTO officialIdDTO = null;
        for (int i = 0; i < json.length(); i++) {
            purcharOrderJson = json.getJSONObject(i);

            //Map DTO
            purchaseOrderDTO = new PurchaseOrderDTO();

            //Map Provider
            companyDTO = new CompanyDTO();
            officialIdJson = purcharOrderJson.getJSONObject("provider");
            officialIdDTO = new OfficialIdDTO();
            officialIdDTO.setOfficialIdType(OfficialIdTypeEnum.valueOf(officialIdJson.getString("officialIdType")));
            officialIdDTO.setOfficialIdNumber(officialIdJson.getString("officialIdNumber"));
            ///Map Official ID of provider
            companyDTO.setOfficialIds(Arrays.asList(officialIdDTO));

            //Map Purchase Order
            purchaseOrderDTO.setCompany(companyDTO);
            var totalPrice = Money.of(purcharOrderJson.getDouble("totalPrice"), "PEN");

            purchaseOrderDTO.setTotalPrice(totalPrice.getNumberStripped());
            purchaseOrderDTO.setPurchaseOrderDetails(wrapPurchaseOrderDetails(purcharOrderJson.getJSONArray("details")));

            purchaseOrderDTOS.add(purchaseOrderDTO);
        }

        return purchaseOrderDTOS;
    }

    private List<PurchaseOrderDetailDTO> wrapPurchaseOrderDetails(JSONArray json) throws JSONException {
        ArrayList<PurchaseOrderDetailDTO> detailDTOS = new ArrayList<>();
        ProductDTO productDTO = null;
        PurchaseOrderDetailDTO purchaseOrderDetailDTO = null;
        JSONObject purchaseOrderDetailJson = null;
        for (int j = 0; j < json.length(); j++) {
            purchaseOrderDetailJson = json.getJSONObject(j);

            //Map Detail
            purchaseOrderDetailDTO = new PurchaseOrderDetailDTO();

            ///Map Product
            productDTO = new ProductDTO();
            productDTO.setSku(purchaseOrderDetailJson.getString("sku"));
            purchaseOrderDetailDTO.setProduct(productDTO);
            purchaseOrderDetailDTO.setQuantity(new BigDecimal(purchaseOrderDetailJson.getDouble("quantity")));

            detailDTOS.add(purchaseOrderDetailDTO);
        }
        return detailDTOS;
    }

}
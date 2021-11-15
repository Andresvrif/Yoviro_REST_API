package com.yoviro.rest.controller;

import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.config.enums.StatusProposalEnum;
import com.yoviro.rest.dto.*;
import com.yoviro.rest.dto.search.SearchProposalDTO;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.security.service.IJWTService;
import com.yoviro.rest.service.interfaces.IProposalService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import com.yoviro.rest.util.StringUtil;
import org.hibernate.jdbc.Work;
import org.javamoney.moneta.Money;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proposal")
public class ProposalRestController {
    @Autowired
    IProposalService proposalService;

    @Autowired
    private IJWTService jwtService;

    @Autowired
    ModelMapper modelMapper;

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
        List<InventoryRequestDTO> inventoryRequestDTOS = wrapInventoryRequestDTOs(jsonObject.getJSONArray("inventoryRequests"));
        List<PurchaseOrderDTO> purchaseOrdersDTOS = wrapPurchaseOrderDTOs(jsonObject.getJSONArray("purchaseOrders"));

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

    private List<InventoryRequestDTO> wrapInventoryRequestDTOs(JSONArray json) throws JSONException {
        ArrayList<InventoryRequestDTO> inventoryRequestDTOS = new ArrayList<>();
        InventoryRequestDTO inventoryRequestDTO = null;
        for (int i = 0; i < json.length(); i++) {
            inventoryRequestDTO = new InventoryRequestDTO();
            inventoryRequestDTO.setInventoryRequestNumber(json.getString(i));

            inventoryRequestDTOS.add(inventoryRequestDTO);
        }

        return inventoryRequestDTOS;
    }

    private List<PurchaseOrderDTO> wrapPurchaseOrderDTOs(JSONArray json) throws JSONException {
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
            purchaseOrderDTO.setPurchaseOrderDetails(wrapPurchaseOrderDetailDTOs(purcharOrderJson.getJSONArray("details")));

            purchaseOrderDTOS.add(purchaseOrderDTO);
        }

        return purchaseOrderDTOS;
    }

    private List<PurchaseOrderDetailDTO> wrapPurchaseOrderDetailDTOs(JSONArray json) throws JSONException {
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

    @GetMapping("/details")
    public Map<String, Object> details(@RequestParam(required = false) String proposalNumber) {
        Proposal proposal = proposalService.findProposalByProposalNumber(proposalNumber);
        if (proposal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposal not found with that number");
        }
        return wrapProposalDetails(proposal);
    }

    private Map<String, Object> wrapProposalDetails(Proposal proposal) {
        Map<String, Object> content;

        //Wrap Inventory Requests
        List<Map> inventoryRequestsJson = proposal.getInventoryRequests().stream().map(e -> wrapInventoryRequestJson(e)).collect(Collectors.toList());
        List<Map> purchaseOrdersJson = proposal.getPurchaseOrders().stream().map(e -> wrapPurchaseOrderJson(e)).collect(Collectors.toList());
        content = Map.ofEntries(
                Map.entry("inventoryRequests", inventoryRequestsJson),
                Map.entry("purchaseOrders", purchaseOrdersJson)
        );

        return content;
    }

    private Map<String, Object> wrapInventoryRequestJson(InventoryRequest inventoryRequest) {
        List<InventoryRequestDetail> details = inventoryRequest.getDetails();
        Person author = inventoryRequest.getAuthor().getPerson();
        Person resident = inventoryRequest.getResident().getPerson();
        return Map.ofEntries(
                Map.entry("inventoryRequestNumber", inventoryRequest.getInventoryRequestNumber()),
                Map.entry("createAt", inventoryRequest.getCreateAt()),
                Map.entry("status", inventoryRequest.getStatus()),
                Map.entry("author", StringUtil.capitalizeWord(author.getFullName())),
                Map.entry("resident", StringUtil.capitalizeWord(resident.getFullName())),
                Map.entry("details", details.stream().map(e -> wrapInventoryRequestDetailJson(e)))
        );
    }

    private Map wrapInventoryRequestDetailJson(InventoryRequestDetail detail) {
        Product product = detail.getProduct();
        return Map.ofEntries(
                Map.entry("sku", product.getSku()),
                Map.entry("productName", StringUtil.capitalizeWord(detail.getProduct().getName())),
                Map.entry("quantity", detail.getQuantity()),
                Map.entry("unitMeasure", product.getUnitMeasure())
        );
    }

    private Map wrapPurchaseOrderJson(PurchaseOrder purchaseOrder) {
        Company company = purchaseOrder.getCompany();
        OfficialId primaryOfficialId = company.getPrimaryOfficialID();
        List<PurchaseOrderDetail> details = purchaseOrder.getPurcharseOrderDetails();
        return Map.ofEntries(
                Map.entry("purchaseOrderNumber", purchaseOrder.getPurchaseOrderNumber()),
                Map.entry("totalPrice", purchaseOrder.getTotalPrice()),
                Map.entry("details", details.stream().map(e -> wrapPurchaseOrderDetailJson(e))),
                Map.entry("status", purchaseOrder.getStatus()),
                Map.entry("createAt", purchaseOrder.getCreateAt()),
                Map.entry("provider", Map.ofEntries(
                        Map.entry("name", company.getName()),
                        Map.entry("officialIdType", primaryOfficialId.getOfficialIdType()),
                        Map.entry("officialIdNumber", primaryOfficialId.getOfficialIdNumber())
                ))
        );
    }

    private Map wrapPurchaseOrderDetailJson(PurchaseOrderDetail detail) {
        return Map.ofEntries(
                Map.entry("sku", detail.getProduct().getSku()),
                Map.entry("unitMeasure", detail.getProduct().getUnitMeasure()),
                Map.entry("productName", StringUtil.capitalizeWord(detail.getProduct().getName())),
                Map.entry("quantity", detail.getQuantity())
        );
    }

    @PostMapping("/evaluate")
    public void evaluate(@RequestHeader(name = "Authorization") String authorization,
                         @RequestBody String json) throws JSONException {
        //Retrieve userName from token
        String token = jwtService.retrieveToken(authorization);
        String userName = jwtService.getUserName(token);

        //Map Evaluate Info
        JSONObject jsonObject = new JSONObject(json);
        Boolean hasProposalNumber = jsonObject.has("proposalNumber");
        Boolean hasStatus = jsonObject.has("status");
        Boolean hasReason = jsonObject.has("reasonForDenied");

        if (!hasProposalNumber)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "proposalNumber is required");

        if (!hasStatus)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status is required");

        String statusCode = jsonObject.getString("status");
        StatusProposalEnum status = StatusProposalEnum.valueOf(statusCode);
        if (status == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The status: " + statusCode + ", no exist");

        if ((status == StatusProposalEnum.CANCELED || status == StatusProposalEnum.REJECTED) && !hasReason) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "reasonForDenied is required");
        }

        ProposalDTO proposalDTO = new ProposalDTO();
        proposalDTO.setProposalNumber(jsonObject.getString("proposalNumber"));
        proposalDTO.setStatus(status);
        if (hasReason) {
            proposalDTO.setReasonForDenied(jsonObject.getString("reasonForDenied"));
        }

        proposalService.updateStatus(userName, proposalDTO);
    }
}
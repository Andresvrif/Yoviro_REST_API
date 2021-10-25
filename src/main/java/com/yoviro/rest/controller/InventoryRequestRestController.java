package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import com.yoviro.rest.dto.*;
import com.yoviro.rest.dto.search.SearchInventoryRequestDTO;
import com.yoviro.rest.dto.search.SearchProductDTO;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.projections.SummaryListInventoryRequestNurseProjection;
import com.yoviro.rest.security.service.IJWTService;
import com.yoviro.rest.service.interfaces.IInventoryRequestService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
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
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/inventoryRequest")
public class InventoryRequestRestController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IInventoryRequestService inventoryRequestService;

    @Autowired
    private IJWTService jwtService;

    @DeleteMapping("")
    public void deleteInventoryRequest(@RequestParam Map<String, String> params) throws JSONException {
        String requestToDelete = params.get("requestToDelete");
        String[] inventoryRequestNumberToDelete = requestToDelete.split(",");
        inventoryRequestService.deleteAllByInventoryRequestNumberIn(Arrays.asList(inventoryRequestNumberToDelete));
    }

    @GetMapping("/mySummaryList")
    public Map<String, Object> summaryList(@RequestHeader(name = "Authorization") String authorization,
                                           @RequestParam(required = false) String search,
                                           @RequestParam(required = true) String page,
                                           @RequestParam(required = true) Boolean ascending) {
        //Retrieve userName from token
        String token = jwtService.retrieveToken(authorization);
        String userName = jwtService.getUserName(token);

        //Resolve UI Params
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable pageable = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE);

        //Call Service and retrieve data
        Page<SummaryListInventoryRequestNurseProjection> pageSummary = inventoryRequestService.summaryListByNurseUserName(search, pageable, userName, ascending);

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.METADATA_TAG, JSONUtil.pageToJson(pageSummary));
        response.put("content", wrapSummaryList(pageSummary.getContent()));

        return response;
    }

    private ArrayList<Map> wrapSummaryList(List<SummaryListInventoryRequestNurseProjection> inventoryRequests) {
        List response = new ArrayList<>();
        Map content = null;
        for (SummaryListInventoryRequestNurseProjection request : inventoryRequests) {
            content = Map.ofEntries(
                    Map.entry("inventoryRequestNumber", request.getInventoryRequestNumber()),
                    Map.entry("resident", request.getResidentFullName() == null ? "" : request.getResidentFullName()),
                    Map.entry("status", request.getStatus()),
                    Map.entry("storeKeeper", request.getStoreKeeperFullName() == null ? "" : request.getStoreKeeperFullName()),
                    Map.entry("proposal", request.getProposalNumber() == null ? "" : request.getProposalNumber()), //TODO debe agarrar la ultima versión
                    Map.entry("createAt", request.getCreateAt())
            );

            response.add(content);
        }

        return (ArrayList<Map>) response;
    }

    @PostMapping("/createOrupdate")
    public String createOrUpdate(@RequestHeader(name = "Authorization") String authorization,
                                 @RequestBody(required = true) String json) throws JSONException, JsonProcessingException {
        //Retrieve userName from token
        String token = jwtService.retrieveToken(authorization);
        String userName = jwtService.getUserName(token);

        //Map Json
        JSONObject jsonObject = new JSONObject(json);
        //Validate json
        if (!jsonObject.has("resident"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "resident object is required");
        if (!jsonObject.has("details"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "details object is required");

        //Map fields and objects
        // Resident Info
        OfficialIdDTO officialIdDTO = objectMapper.readValue(jsonObject.get("resident").toString(), OfficialIdDTO.class);

        // Inventory Request Info
        String inventoryRequestNumber = jsonObject.has("inventoryRequestNumber") ? jsonObject.getString("inventoryRequestNumber").toString() : null;

        // Inventory Request Detail Info
        List<InventoryRequestDetailDTO> details = jsonToInventoryRequestDetails(jsonObject.getJSONArray("details"));
        InventoryRequest inventoryRequest = inventoryRequestService.createOrUpdate(userName, inventoryRequestNumber, officialIdDTO, details);

        return inventoryRequest.getInventoryRequestNumber();
    }

    private List<InventoryRequestDetailDTO> jsonToInventoryRequestDetails(JSONArray jsonDetails) throws JSONException {
        ProductDTO productDTO;
        InventoryRequestDetailDTO requestDetailDTO;
        JSONObject jsonRequestDetail;
        List<InventoryRequestDetailDTO> detailDTOS = new ArrayList<>();

        for (int i = 0; i < jsonDetails.length(); i++) {
            jsonRequestDetail = jsonDetails.getJSONObject(i);

            //Validate json
            if (!jsonRequestDetail.has("sku"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sku is required in details object");

            if (!jsonRequestDetail.has("quantity"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity is required in details object");


            //Instance product
            productDTO = new ProductDTO();
            productDTO.setSku(jsonRequestDetail.getString("sku"));

            //Instance inventory detail
            requestDetailDTO = new InventoryRequestDetailDTO();
            requestDetailDTO.setQuantity(new BigDecimal(jsonRequestDetail.getString("quantity")));
            requestDetailDTO.setProduct(productDTO);

            detailDTOS.add(requestDetailDTO);
        }

        return detailDTOS;
    }

    @GetMapping("/{inventoryRequestNumber}")
    public Map<String, Object> inventoryRequestByReqNumber(@PathVariable String inventoryRequestNumber) throws Exception {
        InventoryRequest inventoryRequest = inventoryRequestService.findInventoryRequestByReqNumber(inventoryRequestNumber);
        if (inventoryRequest == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory request not found with that number");
        }

        return wrapInventoryRequest(inventoryRequest);
    }

    private Map wrapInventoryRequest(InventoryRequest inventoryRequest) {
        Person person = inventoryRequest.getResident().getPerson(); //Resident Info
        OfficialId officialId = person.getPrimaryOfficialID();
        Product product = null;
        Map contentJson = null;
        Map residentJson = null;

        //Map inventory request detail (products)
        List detailsJson = new ArrayList();
        Map detailJson = null;
        for (InventoryRequestDetail detail : inventoryRequest.getDetails()) {
            product = detail.getProduct();
            detailJson = Map.ofEntries(
                    Map.entry("sku", product.getSku()),
                    Map.entry("quantity", detail.getQuantity()),
                    Map.entry("unitMeasure", product.getUnitMeasure()),
                    Map.entry("description", product.getDescription()),
                    Map.entry("name", product.getName())
            );
            detailsJson.add(detailJson);
        }

        //Map resident info
        residentJson = Map.ofEntries(
                Map.entry("firstName", person.getName()),
                Map.entry("secondName", Optional.ofNullable(person.getSecondName())),
                Map.entry("lastName", person.getLastName()),
                Map.entry("secondLastName", Optional.ofNullable(person.getSecondLastName())),
                Map.entry("birthDate", person.getBirthDate()),
                Map.entry("email", Optional.ofNullable(person.getEmail())),
                Map.entry("officlaId", Map.ofEntries(
                        Map.entry("officialIdNumber", officialId.getOfficialIdNumber()),
                        Map.entry("officialIdType", officialId.getOfficialIdType())
                ))
        );

        //Map final json result
        contentJson = Map.ofEntries(
                Map.entry("inventoryRequestNumber", inventoryRequest.getInventoryRequestNumber()),
                Map.entry("resident", residentJson),
                Map.entry("details", detailsJson)
        );

        return contentJson;
    }

    @GetMapping("/search")
    public Map<String, Object> inventoryRequestByReqNumber(@RequestParam(required = false) String inventoryRequestNumber,
                                                           @RequestParam(required = false) InventoryRequestStatusEnum status,
                                                           @RequestParam(required = false) LocalDate startDate,
                                                           @RequestParam(required = false) LocalDate endDate,
                                                           @RequestParam(required = true) String page) {
        //Define Search criteria
        SearchInventoryRequestDTO criteria = new SearchInventoryRequestDTO();
        criteria.setInventoryRequestNumber(inventoryRequestNumber);
        criteria.setStatus(status);
        criteria.setStartDate(startDate);
        criteria.setEndDate(endDate);

        //Define Page criteria
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable pageableByInventoryReqNumber = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("inventoryRequestNumber").ascending());

        Page<InventoryRequest> pageResult = inventoryRequestService.search(pageableByInventoryReqNumber, criteria);
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

    private ArrayList<Map> wrapSearchResult(List<InventoryRequest> inventoryRequests) {
        List dataList = new ArrayList<>();
        Map item = null;
        Person claimant = null; //Person who create the inventory request
        for (InventoryRequest inventoryRequest : inventoryRequests) {
            claimant = inventoryRequest.getAuthor().getPerson();

            item = Map.ofEntries(
                    Map.entry("inventoryRequestNumber", inventoryRequest.getInventoryRequestNumber()),
                    Map.entry("status", inventoryRequest.getStatus()),
                    Map.entry("author", claimant.getFullName()),
                    Map.entry("createAt", inventoryRequest.getCreateAt())
            );

            dataList.add(item);
        }

        return (ArrayList<Map>) dataList;
    }
}
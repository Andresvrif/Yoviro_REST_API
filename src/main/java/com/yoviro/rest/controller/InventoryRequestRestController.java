package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.dto.InventoryRequestDetailDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.ProductDTO;
import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.InventoryRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/summaryList")
    public Map<String, Object> summaryList(@RequestHeader(name = "Authorization") String authorization,
                                           @RequestParam(required = true) String page,
                                           @RequestParam(required = true) Boolean ascending) {
        //Retrieve userName from token
        String token = jwtService.retrieveToken(authorization);
        String userName = jwtService.getUserName(token);

        //Resolve UI Params
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable pageable = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE);

        //Call Service and retrieve data
        Page<SummaryListInventoryRequestNurseProjection> pageSummary = inventoryRequestService.summaryListByNurseUserName(pageable, userName, ascending);

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
            requestDetailDTO.setProductDTO(productDTO);

            detailDTOS.add(requestDetailDTO);
        }

        return detailDTOS;
    }
}
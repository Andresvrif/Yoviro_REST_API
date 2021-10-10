package com.yoviro.rest.controller;

import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.models.entity.InventoryRequest;
import com.yoviro.rest.models.repository.projections.SummaryListInventoryRequestNurseProjection;
import com.yoviro.rest.service.interfaces.IInventoryRequestService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventoryRequest")
public class InventoryRequestRestController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IInventoryRequestService inventoryRequestService;

    @GetMapping("/summaryList")
    public Map<String, Object> summaryList(@RequestParam(required = true) String userName,
                                           @RequestParam(required = true) String page,
                                           @RequestParam(required = true) Boolean ascending) {
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
}
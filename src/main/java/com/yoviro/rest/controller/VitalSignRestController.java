package com.yoviro.rest.controller;

import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.dto.VitalSignDTO;
import com.yoviro.rest.models.repository.projections.SummaryVitalSignProjection;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import com.yoviro.rest.service.interfaces.IVitalSignService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vitalSigns")
public class VitalSignRestController {

    @Autowired
    private IVitalSignService vitalSignService;

    @GetMapping("/summaryList")
    public Map<String, Object> summaryList(@RequestParam(required = false) String search,
                                           @RequestParam(required = true) String page) {
        //Define pagination
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable pageableCriteria = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("firstName").ascending());

        //Call Service
        Page<SummaryVitalSignProjection> summary = search == null ? vitalSignService.findAll(pageableCriteria) : vitalSignService.summaryList(pageableCriteria, search);

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.METADATA_TAG, JSONUtil.pageToJson(summary));
        response.put("content", summary.getContent());

        return response;
    }
}
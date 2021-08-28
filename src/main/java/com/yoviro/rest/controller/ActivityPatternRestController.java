package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.dto.ActivityPatternDTO;
import com.yoviro.rest.dto.AgreementDTO;
import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.repository.projections.AgreementResidentProjection;
import com.yoviro.rest.models.repository.projections.SummaryActivityPatternProjection;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import com.yoviro.rest.service.interfaces.IAgreementService;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activityPatterns")
public class ActivityPatternRestController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private IActivityPatternService activityPatternService;

    @Autowired
    private IAgreementService agreementService;

    @PostMapping("/updateOrCreate")
    public String newActivityPattern(@RequestBody String json) throws JSONException, JSONException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject activityPatternDTOJsonObject = (JSONObject) jsonObject.get("activityPatternDTO");
        JSONArray agreementsJson = activityPatternDTOJsonObject.getJSONArray("agreements");
        activityPatternDTOJsonObject.remove("agreements");

        //Define ACtivity Pattern DTO
        ActivityPatternDTO activityPatternDTO = objectMapper.readValue(activityPatternDTOJsonObject.toString(), ActivityPatternDTO.class);
        activityPatternDTO.setEnable(true);


        List<String> agreementNodes = JSONUtil.<String>transformToList(agreementsJson);
        activityPatternService.createNewActivityPatternWithAgreements(activityPatternDTO, agreementNodes);

        return null;
    }

    @GetMapping("/summaryList")
    public Map<String, Object> summaryList(@RequestParam(required = false) String search,
                                           @RequestParam(required = true) String page) {
        //Define pagination
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable sortedByDescription = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("subject").ascending());

        //Call Service and retrieve data
        Page<SummaryActivityPatternProjection> pageSummary = search != null ? activityPatternService.summaryList(sortedByDescription, search) :
                activityPatternService.summaryList(sortedByDescription);

        //Transform projection to DTO
        List<ActivityPatternDTO> resultDTO = pageSummary.stream()
                .map(activityPattern -> modelMapper.map(activityPattern, ActivityPatternDTO.class))
                .collect(Collectors.toList());

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.METADATA_TAG, JSONUtil.pageToJson(pageSummary));
        response.put("activityPatternDTOs", resultDTO);

        return response;
    }

    @DeleteMapping("")
    public void deleteActivyPatternByPatternCode(@RequestParam Map<String, String> params) {
        String patternCode = params.get("patternCode");
        String[] patternCodesToDelete = patternCode.split(",");
        activityPatternService.deleteAllByPatternCodes(patternCodesToDelete);
    }

    @GetMapping("/agreementResidentRelated")
    public Map<String, Object> agreementResidentRelated(@RequestParam Map<String, String> params) throws Exception {
        //Resolve UI Params
        String pageParam = params.get(AppConfig.PAGE_REQUEST_PARAM_NAME);
        String patternCode = params.get("patternCode");

        //Define pagination
        Integer pageNumber = PageUtil.definePageNumber(pageParam);
        Pageable sortedByFirstName = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("firstName").ascending());

        //Call Service and retrieve data
        Page<AgreementResidentProjection> page = activityPatternService.agreementsResidentRelated(sortedByFirstName, patternCode);

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.METADATA_TAG, JSONUtil.pageToJson(page));
        response.put("residents", page.getContent());

        return response;
    }
}
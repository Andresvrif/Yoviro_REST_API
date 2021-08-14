package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.dto.ActivityPatternDTO;
import com.yoviro.rest.dto.AgreementDTO;
import com.yoviro.rest.models.repository.projections.AgreementResidentProjection;
import com.yoviro.rest.models.repository.projections.SummaryActivityPatternProjection;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
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

    @PostMapping("/newActivityPattern")
    public String newActivityPattern(@RequestBody String json) throws JSONException, JSONException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject activityPatternDTOJsonObject = (JSONObject) jsonObject.get("activityPatternDTO");
        List<AgreementDTO> agreementDTOS = retrieveContractNumberAsAgreementDTOs(activityPatternDTOJsonObject.getJSONArray("agreements"));
        activityPatternDTOJsonObject.remove("agreements");
        //Define ACtivity Pattern DTO
        ActivityPatternDTO activityPatternDTO = objectMapper.readValue(activityPatternDTOJsonObject.toString(), ActivityPatternDTO.class);
        activityPatternDTO.setEnable(true);
        //Define Agreement related to the Activity Pattern
        Set<AgreementDTO> agreements = new HashSet<AgreementDTO>(agreementDTOS.size());
        agreements.addAll(agreementDTOS);
        activityPatternDTO.setAgreements(agreements);

        //Save it
        activityPatternDTO = modelMapper.map(activityPatternService.save(activityPatternDTO), ActivityPatternDTO.class);
        return activityPatternDTO.getPatternCode();
    }

    @GetMapping("/summaryList")
    public Map<String, Object> summaryList(@RequestParam Map<String, String> params) {
        //Resolve UI Params
        String pageParam = params.get(AppConfig.PAGE_REQUEST_PARAM_NAME);
        String qryParam = params.get(AppConfig.SEARCH_REQUEST_PARAM_NAME);

        //Define pagination
        Integer pageNumber = PageUtil.definePageNumber(pageParam);
        Pageable sortedByDescription = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("subject").ascending());

        //Call Service and retrieve data
        Page<SummaryActivityPatternProjection> page = qryParam != null ? activityPatternService.summaryList(sortedByDescription, qryParam) :
                activityPatternService.summaryList(sortedByDescription);

        //Transform projection to DTO
        List<ActivityPatternDTO> resultDTO = page.stream()
                .map(activityPattern -> modelMapper.map(activityPattern, ActivityPatternDTO.class))
                .collect(Collectors.toList());

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.METADATA_TAG, JSONUtil.pageToJson(page));
        response.put("activityPatternDTOs", resultDTO);

        return response;
    }

    @DeleteMapping("")
    public void deleteActivyPatternByPatternCode(@RequestParam Map<String, String> params) {
        String patternCode = params.get("patternCode");
        String[] patternCodesToDelete = patternCode.split(",");
        activityPatternService.deleteAllByPatternCodes(patternCodesToDelete);
    }

    private List<AgreementDTO> retrieveContractNumberAsAgreementDTOs(JSONArray jsonArray) throws JSONException {
        AgreementDTO agreementDTO;
        List<AgreementDTO> agreementDTOS = new ArrayList<AgreementDTO>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String contractNumber = jsonArray.get(i).toString();
            agreementDTO = new AgreementDTO();
            agreementDTO.setAgreementNumber(contractNumber);

            agreementDTOS.add(agreementDTO);
        }

        return agreementDTOS;
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
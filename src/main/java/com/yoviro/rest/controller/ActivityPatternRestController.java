package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.dto.ActivityPatternDTO;
import com.yoviro.rest.models.repository.projections.SummaryActivityPatternProjection;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ActivityPatternDTO newActivityPattern(@RequestBody String json) throws JSONException, JSONException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject(json);
        ActivityPatternDTO activityPatternDTO = objectMapper.readValue(jsonObject.get("activityPatternDTO").toString(), ActivityPatternDTO.class);
        activityPatternDTO = modelMapper.map(activityPatternService.save(activityPatternDTO), ActivityPatternDTO.class);
        return activityPatternDTO;
    }

    @GetMapping("/summaryList")
    public Map<String, Object> summaryList(@RequestParam Map<String, String> params) {
        //Resolve UI Params
        String pageParam = params.get(AppConfig.PAGE_REQUEST_PARAM_NAME);
        pageParam = (pageParam == null || pageParam.compareToIgnoreCase("0") == 0) ? "1" : pageParam;
        Integer pageNumber = Integer.parseInt(pageParam) - 1;

        //Call BD and handle page
        Pageable sortedByDescription = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("subject").ascending());
        Page<SummaryActivityPatternProjection> page = activityPatternService.summaryList(sortedByDescription);
        List<ActivityPatternDTO> resultDTO = page.stream()
                                                 .map(activityPattern -> modelMapper.map(activityPattern, ActivityPatternDTO.class))
                                                 .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.PAGE_RESPONSE_TOTAL_PAGES_NAME, page.getTotalPages());
        response.put(AppConfig.PAGE_RESPONSE_CURRENT_PAGE, page.getNumber() + 1);
        response.put(AppConfig.PAGE_RESPONSE_TOTAL_ELEMENTS, page.getTotalElements());
        response.put("activityPatternDTOs", resultDTO);

        return response;
    }
}

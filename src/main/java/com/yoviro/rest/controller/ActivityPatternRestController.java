package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.dto.ActivityPatternDTO;
import com.yoviro.rest.dto.AgreementDTO;
import com.yoviro.rest.dto.ContractorDTO;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

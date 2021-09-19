package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.dto.ActivityDTO;
import com.yoviro.rest.dto.ActivityPatternDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.VitalSignDTO;
import com.yoviro.rest.models.entity.Resident;
import com.yoviro.rest.models.entity.VitalSign;
import com.yoviro.rest.models.repository.projections.SummaryVitalSignProjection;
import com.yoviro.rest.service.interfaces.IVitalSignService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vitalSigns")
public class VitalSignRestController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

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

    @PostMapping("/createOrUpdate")
    public void createOrUpdate(@RequestBody String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        String idActivity = jsonObject.get("idActivity").toString();
        JSONObject vitalSignJSON = (JSONObject) jsonObject.get("VitalSignDTO");
        JSONObject officialIdJSON = (JSONObject) vitalSignJSON.get("OfficialIdDto");

        //Map Official ID & Vital signs
        OfficialIdDTO officialIdDTO = objectMapper.readValue(officialIdJSON.toString(), OfficialIdDTO.class);
        VitalSignDTO vitalSignDTO = objectMapper.readValue(vitalSignJSON.toString(), VitalSignDTO.class);
        ActivityDTO activityDTO = null;
        if (idActivity != null) {
            activityDTO = new ActivityDTO();
            activityDTO.setId(Long.parseLong(idActivity.toString()));
        }

        //Save it
        vitalSignService.createOrUpdate(officialIdDTO, vitalSignDTO, activityDTO);
    }
}
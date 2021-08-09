package com.yoviro.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.dto.*;
import com.yoviro.rest.service.interfaces.IAgreementService;
import com.yoviro.rest.util.DateUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.DateUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/agreements")
public class AgreementRestController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private IAgreementService agreementService;

    @PostMapping("/newFullAgreement")
    public AgreementDTO newFullAgreement(@RequestBody String json) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(json);
        ContractorDTO contractorDTO = objectMapper.readValue(jsonObject.get("contractorDTO").toString(), ContractorDTO.class);
        ResidentDTO residentDTO = objectMapper.readValue(jsonObject.get("residentDTO").toString(), ResidentDTO.class);
        SubmissionDTO submissionDTO = objectMapper.readValue(jsonObject.get("submissionDTO").toString(), SubmissionDTO.class);

        AgreementDTO agreementDTO = agreementService.createNewFullAgreement(contractorDTO, residentDTO, submissionDTO);
        agreementDTO.setJobs(null);
        return agreementDTO;
    }

    @PostMapping("/cancel")
    public AgreementDTO cancelAgreement(@RequestBody String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        String agreementNumberParam = jsonObject.get("agreementNumber").toString();
        String effectiveDateParam = jsonObject.get("effectiveDate").toString();
        Date effectiveDate = DateUtil.instanceDate(effectiveDateParam);

        //Define Cancellation date
        CancellationDTO cancellationDTO = new CancellationDTO();
        cancellationDTO.setEffectiveDate(effectiveDate);

        //Define Agreement date
        AgreementDTO agreementDTO = new AgreementDTO();
        agreementDTO.setAgreementNumber(agreementNumberParam);

        //Register Agreement cancellation
        agreementDTO = agreementService.cancelAgreement(agreementDTO, cancellationDTO);

        return agreementDTO;
    }
}

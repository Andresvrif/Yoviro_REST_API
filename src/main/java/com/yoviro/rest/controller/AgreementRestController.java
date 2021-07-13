package com.yoviro.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.dto.*;
import com.yoviro.rest.service.interfaces.IAgreementService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

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

        return agreementService.createNewFullAgreement(contractorDTO, residentDTO, submissionDTO);
    }

}

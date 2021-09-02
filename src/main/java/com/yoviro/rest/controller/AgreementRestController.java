package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.dto.*;
import com.yoviro.rest.dto.search.SearchAgreementDTO;
import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.dto.search.SearchJobDTO;
import com.yoviro.rest.dto.search.SearchResidentDTO;
import com.yoviro.rest.handler.JobHandler;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.service.interfaces.IAgreementService;
import com.yoviro.rest.util.DateUtil;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import com.yoviro.rest.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agreements")
public class AgreementRestController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

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
    public String cancelAgreement(@RequestBody String json) throws Exception {
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

        return agreementDTO.getAgreementNumber();
    }

    @GetMapping("/search/agreementAndResident")
    public Map<String, Object> searchAgreementAndResident(@RequestParam(required = false) String firstName,
                                                          @RequestParam(required = false) String secondName,
                                                          @RequestParam(required = false) String firstLastName,
                                                          @RequestParam(required = false) String secondLastName,
                                                          @RequestParam(required = false) OfficialIdTypeEnum officialIDTypeEnum,
                                                          @RequestParam(required = false) String officialIDNumber,
                                                          @RequestParam(required = true) Boolean exactCoincidence,
                                                          @RequestParam(required = true) String page) throws JsonProcessingException, JSONException {
        //Define Search info
        SearchAgreementDTO searchAgreementDTO = new SearchAgreementDTO();
        SearchJobDTO searchJobDTO = new SearchJobDTO();
        SearchResidentDTO searchResidentDTO = new SearchResidentDTO();

        SearchContactDTO searchContactDTO = new SearchContactDTO();
        searchContactDTO.setFirstName(firstName);
        searchContactDTO.setSecondName(secondName);
        searchContactDTO.setFirstLastName(firstLastName);
        searchContactDTO.setSecondLastName(secondLastName);
        searchContactDTO.setOfficialIDType(officialIDTypeEnum);
        searchContactDTO.setOfficialIDNumber(officialIDNumber);
        searchContactDTO.setExactCoincidence(exactCoincidence);

        searchResidentDTO.setSearchContactDTO(searchContactDTO);
        searchJobDTO.setSearchResidentDTO(searchResidentDTO);
        searchAgreementDTO.setSearchJobDTO(searchJobDTO);

        //Define Pageable
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable sortedByFirstName = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("agreementNumber").ascending());

        //Call Controller
        Page<Agreement> pageAgreement = agreementService.searchAgreementsByContact(sortedByFirstName, searchAgreementDTO);
        List<Agreement> dataResult = pageAgreement.getContent().stream().collect(Collectors.toList());

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.METADATA_TAG, JSONUtil.pageToJson(pageAgreement));
        response.put("residents", wrapSearchResult(dataResult));

        return response;
    }

    /***
     * Author : Andrés V.
     * Desc : Wrap search agreement resident result
     * @param agreements
     * @return
     */
    private List<Map<String, Object>> wrapSearchResult(List<Agreement> agreements) {
        Map<String, Object> rowData;
        List<Map<String, Object>> dataContainer = new ArrayList<Map<String, Object>>();

        for (Agreement agreement : agreements) {
            Job job = JobHandler.lastJobFromAgreement(agreement);
            Contact contact = job.getResident().getContact();
            OfficialId primaryOfficialID = contact.getPrimaryOfficialID();

            //Put data
            rowData = new HashMap<String, Object>();
            rowData.put("fullName", StringUtil.capitalizeWord(contact.getFullName()));
            rowData.put("officialIdType", primaryOfficialID.getOfficialIdType());
            rowData.put("officialIdNumber", primaryOfficialID.getOfficialIdNumber());
            rowData.put("agreementNumber", agreement.getAgreementNumber());
            rowData.put("status", JobHandler.getStatusTerm(job, new Date()));

            dataContainer.add(rowData);
        }
        return dataContainer;
    }
}
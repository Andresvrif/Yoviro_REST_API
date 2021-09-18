package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.dto.CancellationDTO;
import com.yoviro.rest.dto.JobDTO;
import com.yoviro.rest.dto.SubmissionDTO;
import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.dto.search.SearchJobDTO;
import com.yoviro.rest.dto.search.SearchResidentDTO;
import com.yoviro.rest.models.entity.Cancellation;
import com.yoviro.rest.models.entity.Job;
import com.yoviro.rest.models.entity.Submission;
import com.yoviro.rest.service.interfaces.IJobService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import org.json.JSONException;
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

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jobs")
public class JobRestController {
    @Autowired
    private IJobService jobService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam(required = false) String firstName,
                                      @RequestParam(required = false) String secondName,
                                      @RequestParam(required = false) String firstLastName,
                                      @RequestParam(required = false) String secondLastName,
                                      @RequestParam(required = false) OfficialIdTypeEnum officialIDTypeEnum,
                                      @RequestParam(required = false) String officialIDNumber,
                                      @RequestParam(required = true) Boolean exactCoincidence,
                                      @RequestParam(required = true) String page) throws JsonProcessingException, JSONException {
        SearchJobDTO searchJobDTO = new SearchJobDTO();
        SearchResidentDTO searchResidentDTO = new SearchResidentDTO();
        SearchContactDTO searchContactDTO = new SearchContactDTO();

        searchContactDTO.setFirstName(firstName);
        searchContactDTO.setSecondName(secondName);
        searchContactDTO.setLastName(firstLastName);
        searchContactDTO.setSecondLastName(secondLastName);
        searchContactDTO.setOfficialIDType(officialIDTypeEnum);
        searchContactDTO.setOfficialIDNumber(officialIDNumber);
        searchContactDTO.setExactCoincidence(exactCoincidence);

        searchResidentDTO.setSearchContactDTO(searchContactDTO);
        searchJobDTO.setSearchResidentDTO(searchResidentDTO);

        //Define Pageable
        Integer pageNumber = PageUtil.definePageNumber(page);

        //Call Controller
        Pageable sortedByJobNumber = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("jobNumber").ascending());
        Page<Job> pageJobs = jobService.search(sortedByJobNumber, searchJobDTO);

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.METADATA_TAG, JSONUtil.pageToJson(pageJobs));
        response.put("jobs", pageJobs.getContent().stream().map(c -> {
            if (c instanceof Submission) {
                return modelMapper.map(c, SubmissionDTO.class);
            } else if (c instanceof Cancellation) {
                return modelMapper.map(c, CancellationDTO.class);
            } else {
                return modelMapper.map(c, JobDTO.class);
            }
        }).collect(Collectors.toList()));

        return response;
    }
}
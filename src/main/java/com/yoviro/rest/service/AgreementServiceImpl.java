package com.yoviro.rest.service;

import com.yoviro.rest.dto.*;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.AgreementRepository;
import com.yoviro.rest.models.repository.ContractorRepository;
import com.yoviro.rest.models.repository.JobRepository;
import com.yoviro.rest.models.repository.ResidentRepository;
import com.yoviro.rest.service.interfaces.IAgreementService;
import com.yoviro.rest.util.ObjectMapperUtils;
import org.modelmapper.*;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgreementServiceImpl implements IAgreementService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private JobRepository jobRepository;

    @Transactional
    @Override
    public AgreementDTO createNewFullAgreement(ContractorDTO contractorDTO,
                                               ResidentDTO residentDTO,
                                               SubmissionDTO submissionDTO) {
        //START ---------------------------------------------------------------------------------
        //Prepare DTOs
        submissionDTO.setEffectiveDate(submissionDTO.getStartDate());
        residentDTO.setEnable(true);

        //CREATE CONTRACTOR
        Contractor contractor = modelMapper.map(contractorDTO, Contractor.class);
        contractorRepository.save(contractor);

        //CREATE RESIDENT
        Resident resident = modelMapper.map(residentDTO, Resident.class);
        resident = residentRepository.save(resident);

        //Create SUBMISSION
        Submission submission = modelMapper.map(submissionDTO, Submission.class);
        submission.setResident(resident);

        Agreement agreement = new Agreement();
        agreement.setContractor(contractor);
        submission.setAgreement(agreement);
        agreement.addJob(submission);
        agreement = agreementRepository.save(agreement);
        agreement.setJobs(null); //TODO FIX THIS
        return modelMapper.map(agreement, AgreementDTO.class);
        //return null;
    }
}
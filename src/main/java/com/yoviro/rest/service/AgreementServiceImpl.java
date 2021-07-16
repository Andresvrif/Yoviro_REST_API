package com.yoviro.rest.service;

import com.yoviro.rest.dto.*;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.AgreementRepository;
import com.yoviro.rest.models.repository.ContractorRepository;
import com.yoviro.rest.models.repository.JobRepository;
import com.yoviro.rest.models.repository.ResidentRepository;
import com.yoviro.rest.service.interfaces.IAgreementService;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

        //Create Agreement
        Agreement agreement = new Agreement();
        agreement.setContractor(contractor);
        submission.setAgreement(agreement);
        agreement.addJob(submission);
        agreement = agreementRepository.save(agreement);

        return modelMapper.map(agreement, AgreementDTO.class);
    }
}
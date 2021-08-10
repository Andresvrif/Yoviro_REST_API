package com.yoviro.rest.service;

import com.yoviro.rest.dto.*;
import com.yoviro.rest.handler.JobHandler;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.AgreementRepository;
import com.yoviro.rest.models.repository.ContractorRepository;
import com.yoviro.rest.models.repository.JobRepository;
import com.yoviro.rest.models.repository.ResidentRepository;
import com.yoviro.rest.service.interfaces.IAgreementService;
import com.yoviro.rest.service.interfaces.IContractorService;
import com.yoviro.rest.service.interfaces.IResidentService;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AgreementServiceImpl implements IAgreementService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private IContractorService contractorService;

    @Autowired
    private IResidentService residentService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobHandler jobHandler;

    @Transactional
    @Override
    public AgreementDTO createNewFullAgreement(ContractorDTO contractorDTO,
                                               ResidentDTO residentDTO,
                                               SubmissionDTO submissionDTO) {
        //Prepare DTOs
        submissionDTO.setEffectiveDate(submissionDTO.getStartDate());
        residentDTO.setEnable(true);

        //CREATE CONTRACTOR
        Contractor contractor = contractorService.getOrCreateContractor(contractorDTO);

        //CREATE RESIDENT
        Resident resident = residentService.getOrCreateResident(residentDTO);

        //Create SUBMISSION
        Submission submission = modelMapper.map(submissionDTO, Submission.class);
        submission.setResident(resident);

        //Create Agreement
        //TODO Antes de crear el contrato, verificar si existe alguno vigente para el residente
        Agreement agreement = new Agreement();
        agreement.setContractor(contractor);
        submission.setAgreement(agreement);
        agreement.addJob(submission);
        agreement = agreementRepository.save(agreement);

        return modelMapper.map(agreement, AgreementDTO.class);
    }

    @Override
    public AgreementDTO cancelAgreement(AgreementDTO agreementDTO,
                                        CancellationDTO cancellationDTO) throws Exception {
        Agreement agreement = agreementRepository.findAgreementByAgreementNumber(agreementDTO.getAgreementNumber());
        if (agreement == null) {
            return null;
        }

        //Before cancel, can be only Submission, Reinstatement and Endorsement
        if (!jobHandler.canBeCanceled(agreement, cancellationDTO.getEffectiveDate())) {
            throw new Exception("Se necesita un contrato vigente para realizar una cancelación");
        }

        //Define Cancellation job based en previous Job
        Job lastJob = jobHandler.lastJobFromAgreement(agreement);
        Cancellation cancellation = modelMapper.map(cancellationDTO, Cancellation.class);
        cancellation.setStartDate(lastJob.getStartDate());
        cancellation.setEndDate(lastJob.getEndDate());
        cancellation.setResident(lastJob.getResident());
        cancellation.setAgreement(agreement);

        jobRepository.save(cancellation);

        return modelMapper.map(agreement, AgreementDTO.class);
    }
}
package com.yoviro.rest.service;

import com.yoviro.rest.dto.*;
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
                                        CancellationDTO cancellationDTO) {
        Agreement agreement = agreementRepository.findAgreementByAgreementNumber(agreementDTO.getAgreementNumber());
        if (agreement == null) {
            return null;
        }

        //Before cancel, can be only Submission, Adjourn, Reinstatement
        System.out.println("****************** START ******************");
        List<Job> jobs = agreement.getJobs();
        for(Job job : jobs){
            if(job instanceof Submission){
                System.out.println("\t----> Submission");
            } else if(job instanceof Cancellation){
                System.out.println("\t----> Cancellation");
            }
        }
        System.out.println("******************  END  ******************");

        Cancellation cancellation = modelMapper.map(cancellationDTO, Cancellation.class);
        cancellation.setAgreement(agreement);

        //jobRepository.save(cancellation);

        return modelMapper.map(agreement, AgreementDTO.class);
    }
}
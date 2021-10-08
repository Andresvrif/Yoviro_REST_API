package com.yoviro.rest.service;

import com.yoviro.rest.dto.*;
import com.yoviro.rest.dto.search.SearchAgreementDTO;
import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.dto.search.SearchJobDTO;
import com.yoviro.rest.dto.search.SearchResidentDTO;
import com.yoviro.rest.handler.JobHandler;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.AgreementRepository;
import com.yoviro.rest.models.repository.JobAgreementRepository;
import com.yoviro.rest.models.repository.specification.handler.JoinColumnProps;
import com.yoviro.rest.models.repository.specification.handler.SearchFilter;
import com.yoviro.rest.models.repository.specification.handler.SearchQuery;
import com.yoviro.rest.models.repository.specification.handler.SpecificationUtil;
import com.yoviro.rest.service.interfaces.IAgreementService;
import com.yoviro.rest.service.interfaces.IContractorService;
import com.yoviro.rest.service.interfaces.IResidentService;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    private JobAgreementRepository jobAgreementRepository;

    @Transactional
    @Override
    public AgreementDTO createNewFullAgreement(ContractorDTO contractorDTO,
                                               ResidentDTO residentDTO,
                                               SubmissionDTO submissionDTO) throws Exception {
        //Prepare DTOs
        submissionDTO.setEffectiveDate(submissionDTO.getStartDate());
        residentDTO.setEnable(true);

        //CREATE CONTRACTOR
        Contractor contractor = contractorService.getOrCreateContractor(contractorDTO);

        //CREATE RESIDENT
        Resident resident = residentService.getOrCreateResident(residentDTO);

        Agreement agreement = new Agreement();
        agreement.setContractor(contractor);
        agreement = agreementRepository.save(agreement);

        //Create SUBMISSION
        Submission submission = modelMapper.map(submissionDTO, Submission.class);
        submission.setResident(resident);
        submission.setAgreement(agreement);
        submission = jobAgreementRepository.save(submission);

        //Create Agreement
        //TODO Antes de crear el contrato, verificar si existe alguno vigente para el residente

        agreement.getJobs().add(submission);
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
        if (!JobHandler.canBeCanceled(agreement, cancellationDTO.getEffectiveDate())) {
            throw new Exception("Se necesita un contrato vigente para realizar una cancelación");
        }

        //Define Cancellation job based en previous Job
        Job lastJob = JobHandler.lastJobFromAgreement(agreement);
        Cancellation cancellation = modelMapper.map(cancellationDTO, Cancellation.class);
        cancellation.setStartDate(lastJob.getStartDate());
        cancellation.setEndDate(lastJob.getEndDate());
        cancellation.setResident(lastJob.getResident());
        cancellation.setAgreement(agreement);

        jobAgreementRepository.save(cancellation);

        return modelMapper.map(agreement, AgreementDTO.class);
    }

    @Override
    public Page<Agreement> searchAgreementsByContact(Pageable pageable,
                                                     SearchAgreementDTO searchAgreementDTO) {
        SearchJobDTO searchJobDTO = searchAgreementDTO.getSearchJobDTO();
        SearchResidentDTO searchResidentDTO = searchJobDTO.getSearchResidentDTO();
        SearchContactDTO searchContactDTO = searchResidentDTO.getSearchPersonDTO();

        //Define Search Criteria
        SearchQuery qry = new SearchQuery();
        List<SearchFilter> jobCriteria = JobServiceImpl.instanceJobCriteria(searchJobDTO);
        List<SearchFilter> residentCriteria = ResidentServiceImpl.instanceResidentCriteria(searchResidentDTO); //Contact Filter
        List<SearchFilter> contactCriteria = ContactServiceImpl.instanceContactCriteria(searchContactDTO); //Contact Filter
        List<SearchFilter> officialIDCriteria = OfficialIdServiceImpl.instanceContactSearchQry(searchContactDTO); //OfficialID Filter

        JoinColumnProps joinColumnPropsContactOfficialID = new JoinColumnProps();
        joinColumnPropsContactOfficialID.setJoinColumnName("officialIds");
        joinColumnPropsContactOfficialID.setSearchFilter(officialIDCriteria);

        JoinColumnProps joinColumnPropsResidentAndContact = new JoinColumnProps();
        joinColumnPropsResidentAndContact.setJoinColumnName("person");
        joinColumnPropsResidentAndContact.setSearchFilter(contactCriteria);
        joinColumnPropsResidentAndContact.setSubJoinColumnProps(joinColumnPropsContactOfficialID);

        JoinColumnProps joinColumnPropsJobAndResident = new JoinColumnProps();
        joinColumnPropsJobAndResident.setJoinColumnName("resident");
        joinColumnPropsJobAndResident.setSearchFilter(residentCriteria);
        joinColumnPropsJobAndResident.setSubJoinColumnProps(joinColumnPropsResidentAndContact);

        JoinColumnProps joinColumnPropsAgreementAndJob = new JoinColumnProps();
        joinColumnPropsAgreementAndJob.setJoinColumnName("jobs");
        joinColumnPropsAgreementAndJob.setSearchFilter(jobCriteria);
        joinColumnPropsAgreementAndJob.setSubJoinColumnProps(joinColumnPropsJobAndResident);


        qry.addJoinColumnProp(joinColumnPropsAgreementAndJob);
        Specification<Agreement> specification = SpecificationUtil.bySearchQuery(qry, Agreement.class, Boolean.TRUE);

        return agreementRepository.findAll(specification, pageable);
    }

    @Override
    public Agreement findAgreementByAgreementNumber(String agreementNumber) {
        return agreementRepository.findAgreementByAgreementNumber(agreementNumber);
    }
}
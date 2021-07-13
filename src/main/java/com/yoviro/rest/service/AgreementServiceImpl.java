package com.yoviro.rest.service;

import com.yoviro.rest.dto.AgreementDTO;
import com.yoviro.rest.dto.ContractorDTO;
import com.yoviro.rest.dto.ResidentDTO;
import com.yoviro.rest.dto.SubmissionDTO;
import com.yoviro.rest.models.entity.Contractor;
import com.yoviro.rest.models.entity.Resident;
import com.yoviro.rest.models.repository.ContractorRepository;
import com.yoviro.rest.models.repository.ResidentRepository;
import com.yoviro.rest.service.interfaces.IAgreementService;
import org.modelmapper.ModelMapper;
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

    @Transactional
    @Override
    public AgreementDTO createNewFullAgreement(ContractorDTO contractorDTO,
                                               ResidentDTO residentDTO,
                                               SubmissionDTO submissionDTO) {
        //Create CONTRACT
        //TODO if the id comes, bring data accord ID
        Contractor contractor = modelMapper.map(contractorDTO, Contractor.class);
        contractor = contractorRepository.save(contractor);

        //Create RESIDENT
        //TODO if the id comes, bring data accord ID
        residentDTO.setEnable(Boolean.TRUE);
        Resident resident = modelMapper.map(residentDTO, Resident.class);
        resident = residentRepository.save(resident);

        System.out.println("contractor ------------------------------------> " + contractor);
        System.out.println("resident   ------------------------------------> " + resident);
        //TODO crear agreement con solicitud
        return null;
    }
}
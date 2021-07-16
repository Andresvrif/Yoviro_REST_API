package com.yoviro.rest.service;

import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.ContractorDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.repository.ContractorRepository;
import com.yoviro.rest.models.repository.projections.ContratanteConContactoProjection;
import com.yoviro.rest.models.entity.Contractor;
import com.yoviro.rest.service.interfaces.IContractorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractorServiceImpl implements IContractorService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ContractorRepository contractorRepository;

    @Override
    public Contractor save(ContractorDTO contractorDTO) {
        Contractor contractor = modelMapper.map(contractorDTO, Contractor.class);
        return contractorRepository.save(contractor);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Contractor getOrCreateContractor(ContractorDTO contractorDTO) {
        ContactDTO contactDTO = contractorDTO.getContact();
        OfficialIdDTO officialIdDTO = contactDTO.getOfficialIds().get(0);
        Contractor contractor = contractorRepository.findByOfficialID(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());
        return contractor != null ? contractor : save(contractorDTO);
    }
}
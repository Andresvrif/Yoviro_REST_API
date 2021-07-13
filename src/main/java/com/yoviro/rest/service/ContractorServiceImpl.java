package com.yoviro.rest.service;

import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.ContractorDTO;
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
    public ContractorDTO save(ContractorDTO contractorDTO) {
        Contractor contractor = modelMapper.map(contractorDTO, Contractor.class);
        contractor = contractorRepository.save(contractor);
        return modelMapper.map(contractor, ContractorDTO.class);
    }

    @Override
    public void delete(Long id) {

    }
}
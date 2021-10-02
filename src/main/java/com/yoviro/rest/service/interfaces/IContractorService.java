package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.ContractorDTO;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.Contractor;

public interface IContractorService {
    public Contractor save(ContractorDTO contractorDTO);

    public void delete(Long id);

    public Contractor getOrCreateContractor(ContractorDTO contractorDTO) throws Exception;

}
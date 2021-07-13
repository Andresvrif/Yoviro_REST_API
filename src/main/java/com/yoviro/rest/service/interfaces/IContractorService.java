package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.ContractorDTO;
import com.yoviro.rest.models.repository.projections.ContratanteConContactoProjection;
import com.yoviro.rest.models.entity.Contractor;

import java.util.List;

public interface IContractorService {
    public ContractorDTO save(ContractorDTO contractorDTO);
    public void delete(Long id);
}

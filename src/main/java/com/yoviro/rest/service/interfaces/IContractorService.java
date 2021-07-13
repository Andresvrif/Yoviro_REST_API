package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.models.repository.projections.ContratanteConContactoProjection;
import com.yoviro.rest.models.entity.Contractor;

import java.util.List;

public interface IContractorService {
    //Querys
    public List<Contractor> findAll();
    public List<Contractor> findAllWithNoRelations();
    public void save(Contractor contractor);
    public void delete(Long id);

    //Projections
    List<ContratanteConContactoProjection> findAllByContactoNotNull();

}

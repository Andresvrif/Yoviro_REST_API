package com.yoviro.rest.models.service.interfaces;

import com.yoviro.rest.models.dao.projections.ContratanteConContactoProjection;
import com.yoviro.rest.models.entity.Contacto;
import com.yoviro.rest.models.entity.Contratante;

import java.util.List;

public interface IContratanteService {
    //Querys
    public List<Contratante> findAll();
    public List<Contratante> findAllWithNoRelations();
    public void save(Contratante contratante);
    public void delete(Long id);

    //Projections
    List<ContratanteConContactoProjection> findAllByContactoNotNull();

}

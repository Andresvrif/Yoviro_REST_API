package com.yoviro.rest.models.service;

import com.yoviro.rest.models.dao.IContratanteDao;
import com.yoviro.rest.models.dao.projections.ContratanteProjection;
import com.yoviro.rest.models.entity.Contratante;
import com.yoviro.rest.models.service.interfaces.IContratanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratanteServiceImpl implements IContratanteService {
    @Autowired
    private IContratanteDao contratanteDao;

    @Override
    public List<Contratante> findAll() {
        return null;
    }

    @Override
    public List<Contratante> findAllWithNoRelations() {
        var result = contratanteDao.findAllById(1L, ContratanteProjection.class);
        result.forEach(e->{
            System.out.println("\t e - ID:" + e.getId());
        });
        System.out.println("----------------------------------------------");

        return (List<Contratante>) contratanteDao.findAllById(1L, Contratante.class);
    }

    @Override
    public void save(Contratante contratante) {
        //contratanteDao.save(contratante);
    }

    @Override
    public void delete(Long id) {
        //contratanteDao.deleteById(id);
    }
}
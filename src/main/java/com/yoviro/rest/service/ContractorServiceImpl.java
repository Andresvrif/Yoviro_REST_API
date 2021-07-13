package com.yoviro.rest.service;

import com.yoviro.rest.models.repository.ContractorRepository;
import com.yoviro.rest.models.repository.projections.ContratanteConContactoProjection;
import com.yoviro.rest.models.entity.Contractor;
import com.yoviro.rest.service.interfaces.IContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractorServiceImpl implements IContractorService {
    @Autowired
    private ContractorRepository contratanteDao;

    @Override
    public List<Contractor> findAll() {
        return null;
    }

    @Override
    public List<Contractor> findAllWithNoRelations() {
        var result = contratanteDao.findAllById(1L, ContratanteConContactoProjection.class);
        result.forEach(e -> {
            System.out.println("\t e - ID:" + e.getId());
            System.out.println("\t e - NumeroContrato:" + e.getNumeroDeContratante());
            System.out.println("\t e - NumeroContrato:" + e.getNumeroDeContratante());
        });
        System.out.println("----------------------------------------------");

        return (List<Contractor>) contratanteDao.findAllById(1L, Contractor.class);
    }

    @Override
    public void save(Contractor contractor) {
        //contratanteDao.save(contratante);
    }

    @Override
    public void delete(Long id) {
        //contratanteDao.deleteById(id);
    }

    @Override
    public List<ContratanteConContactoProjection> findAllByContactoNotNull() {
        Pageable paging = PageRequest.of(0, 5, Sort.by("contractorNumber"));
        return contratanteDao.findAllByContactNotNull(paging, ContratanteConContactoProjection.class);
    }
}
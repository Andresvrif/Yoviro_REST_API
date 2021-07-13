package com.yoviro.rest.service;

import com.yoviro.rest.models.repository.OfficialIdRepository;
import com.yoviro.rest.models.entity.OfficialId;
import com.yoviro.rest.service.interfaces.IOfficialIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficialIdServiceImpl implements IOfficialIdService {
    @Autowired
    private OfficialIdRepository documentoDeIdentidadDao;

    @Override
    public List<OfficialId> findAll() {
        return (List<OfficialId>) documentoDeIdentidadDao.findAll();
    }

    @Override
    public void save(OfficialId officialId) {
        documentoDeIdentidadDao.save(officialId);
    }

    @Override
    public void delete(Long id) {
        documentoDeIdentidadDao.deleteById(id);
    }
}

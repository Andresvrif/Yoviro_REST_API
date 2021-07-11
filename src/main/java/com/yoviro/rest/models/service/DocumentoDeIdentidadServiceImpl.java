package com.yoviro.rest.models.service;

import com.yoviro.rest.models.dao.IDocumentoDeIdentidadDao;
import com.yoviro.rest.models.entity.DocumentoDeIdentidad;
import com.yoviro.rest.models.service.interfaces.IDocumentoDeIdentidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoDeIdentidadServiceImpl implements IDocumentoDeIdentidadService {
    @Autowired
    private IDocumentoDeIdentidadDao documentoDeIdentidadDao;

    @Override
    public List<DocumentoDeIdentidad> findAll() {
        return (List<DocumentoDeIdentidad>) documentoDeIdentidadDao.findAll();
    }

    @Override
    public void save(DocumentoDeIdentidad documentoDeIdentidad) {
        documentoDeIdentidadDao.save(documentoDeIdentidad);
    }

    @Override
    public void delete(Long id) {
        documentoDeIdentidadDao.deleteById(id);
    }
}

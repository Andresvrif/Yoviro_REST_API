package com.yoviro.rest.models.service.interfaces;
import com.yoviro.rest.models.entity.Contacto;
import com.yoviro.rest.models.entity.DocumentoDeIdentidad;

import java.util.List;

public interface IDocumentoDeIdentidadService {
    public List<DocumentoDeIdentidad> findAll();
    public void save(DocumentoDeIdentidad documentoDeIdentidad);
    public void delete(Long id);
}

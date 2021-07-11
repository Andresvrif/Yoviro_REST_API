package com.yoviro.rest.models.dao;

import com.yoviro.rest.models.entity.Contacto;
import com.yoviro.rest.models.entity.DocumentoDeIdentidad;
import org.springframework.data.repository.CrudRepository;

public interface IDocumentoDeIdentidadDao extends CrudRepository<DocumentoDeIdentidad, Long> {
}

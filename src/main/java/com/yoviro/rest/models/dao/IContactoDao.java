package com.yoviro.rest.models.dao;

import com.yoviro.rest.models.entity.Contacto;
import org.springframework.data.repository.CrudRepository;

public interface IContactoDao extends CrudRepository<Contacto, Long> {
}

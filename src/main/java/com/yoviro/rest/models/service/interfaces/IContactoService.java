package com.yoviro.rest.models.service.interfaces;

import com.yoviro.rest.models.entity.Contacto;

import java.util.List;

public interface IContactoService {
    public List<Contacto> findAll();
    public void save(Contacto contacto);
    public void delete(Long id);
}

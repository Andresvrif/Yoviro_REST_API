package com.yoviro.rest.models.service;

import com.yoviro.rest.models.dao.IContactoDao;
import com.yoviro.rest.models.entity.Contacto;
import com.yoviro.rest.models.service.interfaces.IContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoServiceImpl implements IContactoService {

    @Autowired
    private IContactoDao contactoDao;

    @Override
    public List<Contacto> findAll() {
        return (List<Contacto>) contactoDao.findAll();
    }

    @Override
    public void save(Contacto contacto) {
        contactoDao.save(contacto);
    }

    @Override
    public void delete(Long id) {
        contactoDao.deleteById(id);
    }
}

package com.yoviro.rest.controllers;

import com.yoviro.rest.models.entity.Contacto;
import com.yoviro.rest.models.service.interfaces.IContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/contactos")
public class ContactoRestController {

    @Autowired
    private IContactoService contactoService;

    @GetMapping(value = "/listar")
    public List<Contacto> listar() {
        return contactoService.findAll();
    }
}

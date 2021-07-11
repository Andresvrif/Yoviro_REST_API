package com.yoviro.rest.controllers;

import com.yoviro.rest.models.entity.Contratante;
import com.yoviro.rest.models.service.interfaces.IContratanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contratantes")
public class ContratanteRestController {

    @Autowired
    private IContratanteService contratanteService;

    @GetMapping(value = "/listar")
    public List<Contratante> listar() {
        List<Contratante> result = contratanteService.findAllWithNoRelations();
        return result;
    }
}

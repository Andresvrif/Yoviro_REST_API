package com.yoviro.rest.controllers;

import com.yoviro.rest.models.dao.projections.ContratanteConContactoProjection;
import com.yoviro.rest.models.entity.Contacto;
import com.yoviro.rest.models.entity.Contratante;
import com.yoviro.rest.models.service.interfaces.IContratanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratantes")
public class ContratanteRestController {

    @Autowired
    private IContratanteService contratanteService;

    @PostMapping
    public Contratante nuevoContratante(@RequestBody Contratante contratante){
        return null;
    }

    @GetMapping(value = "/listar")
    public List<ContratanteConContactoProjection> listar() {
        List<ContratanteConContactoProjection> result = contratanteService.findAllByContactoNotNull();
        return result;
    }
}

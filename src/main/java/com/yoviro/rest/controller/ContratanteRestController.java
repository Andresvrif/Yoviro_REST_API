package com.yoviro.rest.controller;

import com.yoviro.rest.models.repository.projections.ContratanteConContactoProjection;
import com.yoviro.rest.models.entity.Contractor;
import com.yoviro.rest.service.interfaces.IContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratantes")
public class ContratanteRestController {

    @Autowired
    private IContractorService contratanteService;

    @PostMapping
    public Contractor nuevoContratante(@RequestBody Contractor contractor){
        return null;
    }

    @GetMapping(value = "/listar")
    public List<ContratanteConContactoProjection> listar() {
        List<ContratanteConContactoProjection> result = contratanteService.findAllByContactoNotNull();
        return result;
    }
}

package com.yoviro.rest.config;

import com.yoviro.rest.config.typekey.Gender;
import com.yoviro.rest.config.typekey.OfficialId;
import com.yoviro.rest.config.typekey.OptionTypekey;
import com.yoviro.rest.models.dao.IContratanteDao;
import com.yoviro.rest.models.entity.Contacto;
import com.yoviro.rest.models.entity.Contratante;
import com.yoviro.rest.models.entity.Contrato;
import com.yoviro.rest.models.service.interfaces.IDocumentoDeIdentidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
public class AppConfig {

    /*@Autowired
    IContratanteDao contratanteDao;*/

    @Autowired
    private IDocumentoDeIdentidadService documentoDeIdentidadService;

    @Transactional(readOnly = true)
    @EventListener(ApplicationReadyEvent.class)
    public void afterBoot() {
        /*
        List<Contratante> contratantes = (List<Contratante>) contratanteDao.findAll();
        contratantes.forEach(e -> {
            System.out.println("NumeroDeContratante -> " + e.getNumeroDeContratante());
            List<Contrato> contratos = e.getContratos();
            for (Contrato contrato : contratos) {
                System.out.println("\t\t Contrato -> " + contrato.getNumeroContrato());
            }
        });
        */
        //TODO realizar lógica despues del boteo
    }
}
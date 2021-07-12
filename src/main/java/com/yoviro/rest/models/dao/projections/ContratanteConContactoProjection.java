package com.yoviro.rest.models.dao.projections;

import com.yoviro.rest.models.entity.Contacto;

/**
 * Author : Andrés V.
 * Desc: Las proyecciones ayudan a que la query se ejecute con una mejor performance
 */
public interface ContratanteConContactoProjection {
    Long getId();
    String getNumeroDeContratante();
    Contacto getContacto();

    default String getFullName() {
        return getContacto().getNombre().concat(" ").concat(getContacto().getApellidoPaterno());
    }
}
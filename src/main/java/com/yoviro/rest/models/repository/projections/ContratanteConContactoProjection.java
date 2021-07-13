package com.yoviro.rest.models.repository.projections;

import com.yoviro.rest.models.entity.Contact;

/**
 * Author : Andrés V.
 * Desc: Las proyecciones ayudan a que la query se ejecute con una mejor performance, limitando los campos que se traen desde BD
 */
public interface ContratanteConContactoProjection {
    Long getId();
    String getNumeroDeContratante();
    Contact getContacto();

    default String getFullName() {
        //return getContacto().getName().concat(" ").concat(getContacto().getSurname());
        return null;
    }
}
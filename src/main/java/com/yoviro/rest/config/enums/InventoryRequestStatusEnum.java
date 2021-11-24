package com.yoviro.rest.config.enums;

public enum InventoryRequestStatusEnum {
    PENDING, //Pendiente, Solicitud creada por el trabajador
    IN_PROGRESS, //En progreso, Solicitud relacionada a una propuesta
    COMPLETED, //Despachado, Solicitud despachada
    REJECTED //Rechazado, Rechazada
/*    OBSERVED, //Observado*/
}
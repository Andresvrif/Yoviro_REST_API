package com.yoviro.rest.config.enums;

public enum PurcharseOrderEnum {
    QUOTED, //Cotizada, enviada a la propuesta
    BOUGHT, //Comprada, cambia de estado cuando la propuesta a sido aprobada
    RECEIVED, //Recibida, cambia de estado cuando a sido recibida,
    REJECTED //Rechazada, porque muy cara, etc...
}
package com.yoviro.rest.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleGrantedAuthorityMixin {

    //Inyecta la propiedad del Json
    @JsonCreator
    public SimpleGrantedAuthorityMixin(@JsonProperty("authority") String role) { }
}
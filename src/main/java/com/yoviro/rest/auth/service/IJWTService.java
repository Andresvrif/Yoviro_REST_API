package com.yoviro.rest.auth.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

//Como buena practica se suele crear una interfaz, de forma que si cambia la fuente, se aplica otro Impl
public interface IJWTService {

    public String create(Authentication auth) throws IOException;
    public String retrieveToken(String header);
    public Boolean validate(String token);
    public Claims getClaims(String token);
    public String getUserName(String token);
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;
}
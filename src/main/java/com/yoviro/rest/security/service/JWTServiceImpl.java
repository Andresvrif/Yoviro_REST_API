package com.yoviro.rest.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.security.SimpleGrantedAuthorityMixin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Author : Andrés V.
 * Desc   : Creates and handle Json Web Token (JWt)
 */
@Component
public class JWTServiceImpl implements IJWTService {

    public final static String SECRET = "dSpIF2zjKPWJ8dBuULPiu7@M$n$A5%fmRbuk6d";
    public final static Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    public final static long EXPIRATION_DATE = 3600000L * 2; //2hr
    public final static String TOKEN_PREFIX = "Bearer ";
    public final static String HEADER_STRING = "Authorization";


    @Override
    public String create(Authentication auth) throws IOException {
        var user = ((User) auth.getPrincipal());
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .signWith(SECRET_KEY)
                .setIssuedAt(new Date()) //Fecha de inicio de vigencia del token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE)) //3600000 <-> 1hr
                .compact();
    }

    @Override
    public String retrieveToken(String header) {
        return header != null && header.startsWith(TOKEN_PREFIX) ? header.replace(TOKEN_PREFIX, "") : null;
    }

    @Override
    public Boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String getUserName(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

        return authorities;
    }
}

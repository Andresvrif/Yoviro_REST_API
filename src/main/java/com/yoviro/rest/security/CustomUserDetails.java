package com.yoviro.rest.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/***
 * Author : Andrés V.
 * Desc : Wrap custom info related to the user login.
 */
public class CustomUserDetails extends User implements UserDetails {
    private String name;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private LocalDate birthDate;

    public CustomUserDetails(com.yoviro.rest.models.entity.User user,
                             Collection<? extends GrantedAuthority> authorities,
                             String companyName) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.name = companyName;
    }

    public CustomUserDetails(com.yoviro.rest.models.entity.User user,
                             Collection<? extends GrantedAuthority> authorities,
                             String firstName,
                             String secondName,
                             String lastName,
                             String secondLastName,
                             LocalDate birthDate) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.name = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public String getBirthDate() {
        if (birthDate == null) return null;
        return birthDate.format(DateTimeFormatter.ISO_DATE);
        //return new Date(); //Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
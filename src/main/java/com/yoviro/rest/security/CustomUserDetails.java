package com.yoviro.rest.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.Worker;
import net.bytebuddy.asm.Advice;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/***
 * Author : Andrés V.
 * Desc : Wrap custom info related to the user login.
 */
public class CustomUserDetails extends User implements UserDetails {
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private Date birthDate;

    public CustomUserDetails(com.yoviro.rest.models.entity.User user,
                             Collection<? extends GrantedAuthority> authorities,
                             String firstName,
                             String secondName,
                             String lastName,
                             String secondLastName,
                             Date birthDate) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public Date getBirthDate() {
        return birthDate;
    }
}
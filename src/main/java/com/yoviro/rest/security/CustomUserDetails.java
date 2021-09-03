package com.yoviro.rest.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;

public class CustomUserDetails extends User implements UserDetails {
    private com.yoviro.rest.models.entity.User user;

    public CustomUserDetails(com.yoviro.rest.models.entity.User user,
                             String username,
                             String password,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.user = user;
    }

    public CustomUserDetails(String username,
                             String password,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getFirstName(){
        //return user.getFirstName();
        return null;
    }

    public String getSecondName(){
        //return user.getSecondName();
        return null;
    }

    public String getLastName(){
        //return user.getLastName();
        return null;
    }

    public String getSecondLastName(){
        //return user.getSecondLastName();
        return null;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public Date getBirthDate(){
        //return user.getBirthDate();
        return null;
    }
}
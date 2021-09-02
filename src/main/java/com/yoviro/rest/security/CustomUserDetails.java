package com.yoviro.rest.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

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

    public com.yoviro.rest.models.entity.User getUser() {
        return user;
    }

    public void setUser(com.yoviro.rest.models.entity.User user) {
        this.user = user;
    }
}
package com.yoviro.rest.service;

import com.yoviro.rest.models.repository.UserRepository;
import com.yoviro.rest.models.entity.Role;
import com.yoviro.rest.models.entity.User;
import com.yoviro.rest.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/***
 * Author : Andrés V.
 * Desc   : Defines the user detail for DAO login purposes
 */
@Service("jpaUserDetailService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usuarioDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + "no existe en el sistema!");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
        }

        if (authorities.isEmpty()) {
            throw new UsernameNotFoundException("Error login: no existe el usuario '" + username + "', no tiene roles asignado!");
        }

        //return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
        return new CustomUserDetails(user, user.getUsername(), user.getPassword(), authorities);
    }
}

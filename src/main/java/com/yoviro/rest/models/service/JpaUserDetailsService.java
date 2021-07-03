package com.yoviro.rest.models.service;

import com.yoviro.rest.models.dao.IUsuarioDao;
import com.yoviro.rest.models.entity.Role;
import com.yoviro.rest.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
    private IUsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Username " + username +"no existe en el sistema!");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if(authorities.isEmpty()){
            throw new UsernameNotFoundException("Error login: no existe el usuario '" + username + "', no tiene roles asignado!");
        }

        return new User(usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEnabled(), true, true, true, authorities);
    }
}

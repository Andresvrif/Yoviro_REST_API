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

import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario == null) {
            //logger.error("Error login: no existe el usuario '" + s + "'");
            throw new UsernameNotFoundException("Username " + username +"no existe en el sistema!");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Role role : usuario.getRoles()) {
            //logger.info("Role : ".concat(role.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if(authorities.isEmpty()){
            //logger.error("Error login: no existe el usuario '" + s + "', no tiene roles asignado!");
            throw new UsernameNotFoundException("Error login: no existe el usuario '" + username + "', no tiene roles asignado!");
        }

        return new User(usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEnabled(), true, true, true, authorities);
    }
}

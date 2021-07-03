package com.yoviro.rest;

import com.yoviro.rest.auth.filter.JWTAuthenticationFilter;
import com.yoviro.rest.auth.filter.JWTAuthorizationFilter;
import com.yoviro.rest.auth.service.IJWTService;
import com.yoviro.rest.models.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IJWTService jwtService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
            .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService))
            .csrf().disable() //csrf es para formularios, no tiene sentido q vaya habilitado
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Deshabilitar el uso de sesion
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(jpaUserDetailsService) //Se obtiene el usuario de BD, al utilizar UserDetailsService se infiere que se va a utilizar un DAOProvider, por ende no es necesario definirlo
             .passwordEncoder(passwordEncoder);    //Se establece el codificador
    }
}

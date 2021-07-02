package com.yoviro.rest.auth.filter;

import com.yoviro.rest.auth.service.IJWTService;
import com.yoviro.rest.auth.service.JWTServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private IJWTService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  IJWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JWTServiceImpl.HEADER_STRING);
        if (requiresAuthentication(header)) {
            chain.doFilter(request, response);
            return;
        }
        String token = jwtService.retrieveToken(header);
        UsernamePasswordAuthenticationToken authentication = null;
        if (jwtService.validate(token)) {
            authentication = new UsernamePasswordAuthenticationToken(jwtService.getUserName(token), null, jwtService.getRoles(token));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    protected boolean requiresAuthentication(String header) {
        return header == null || !header.startsWith(JWTServiceImpl.TOKEN_PREFIX);
    }
}
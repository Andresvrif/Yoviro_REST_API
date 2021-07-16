package com.yoviro.rest.config;

import com.yoviro.rest.config.mapper.MapperConfig;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MvcConfig {

    @Value("#{'${allowed.cors}'.split(',')}")
    private List<String> availableCors;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(availableCors);
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        return configuration.applyPermitDefaultValues();
    }

    @Bean
    public ModelMapper modelMapper() {
        return MapperConfig.config(new ModelMapper());
    }
}
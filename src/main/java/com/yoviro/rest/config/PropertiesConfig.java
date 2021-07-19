package com.yoviro.rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.Date;

@Configuration
@PropertySources({
        @PropertySource("classpath:properties/typekey.properties")
})
public class PropertiesConfig {
}

package com.yoviro.rest.config;

import com.yoviro.rest.service.interfaces.IOfficialIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class AppConfig {



    @Transactional(readOnly = true)
    @EventListener(ApplicationReadyEvent.class)
    public void afterBoot() {
        //TODO realizar lógica despues del boteo
    }
}
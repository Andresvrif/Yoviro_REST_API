package com.yoviro.rest.config;

import com.yoviro.rest.service.interfaces.IOfficialIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.awt.font.TextHitInfo;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class AppConfig {

    public static Integer EFFECTIVE_DATE = null;

    @Value("${effectivedate.hour}")
    public String _effectiveDateHour;

    @Transactional(readOnly = true)
    @EventListener(ApplicationReadyEvent.class)
    public void afterBoot() {
        //TODO realizar lógica despues del boteo
        AppConfig.EFFECTIVE_DATE = Integer.parseInt(_effectiveDateHour);
    }
}
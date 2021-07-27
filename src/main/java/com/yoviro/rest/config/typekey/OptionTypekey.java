package com.yoviro.rest.config.typekey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@ConfigurationProperties(prefix = "options")
public class OptionTypekey {
    private String code;
    private Boolean enable;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    //<minute> <hour> <day-of-month> <month> <day-of-week> <command>
    //@Scheduled(cron = "*/60 * * * * *")
    public void reportCurrentTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm");
        System.out.print("\nHola desde batch!!!!!!!!!!!!!!!!!!" + simpleDateFormat.format(date));
    }
}
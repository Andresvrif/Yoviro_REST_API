package com.yoviro.rest.config.typekey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "typekey.officialid")
public class OfficialId extends GeneralTypekey {
    @Value("${typekey.officialid.options[0].code}")
    private String dniCode;
    public static OptionTypekey DNI;

    @Value("${typekey.officialid.options[1].code}")
    private String passportCode;
    public static OptionTypekey PASSPORT;

    @Override
    public void afterConstruct() {
        DNI = getOptionTypekeyByCode(dniCode);
        PASSPORT = getOptionTypekeyByCode(passportCode);
    }
}

package com.yoviro.rest.config.typekey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "typekey.gender")
public class Gender extends GeneralTypekey {
    @Value("${typekey.gender.options[0].code}")
    private String maleCode;
    public static OptionTypekey MALE;

    @Value("${typekey.gender.options[1].code}")
    private String femaleCode;
    public static OptionTypekey FEMALE;

    @Override
    public void afterConstruct() {
        MALE = getOptionTypekeyByCode(maleCode);
        FEMALE = getOptionTypekeyByCode(femaleCode);
    }
}

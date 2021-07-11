package com.yoviro.rest.config.typekey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "typekey.job")
public class Job extends GeneralTypekey {
    @Value("${typekey.job.options[0].code")
    private String submissionCode;
    public static OptionTypekey SUBMISSION;

    @Override
    public void afterConstruct() {
        SUBMISSION = getOptionTypekeyByCode(submissionCode);
    }
}

package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;

@Entity
@DiscriminatorValue("SUBMISSION")
public class Submission extends Job {
    @PrePersist
    public void PrePersist() {
        this.createAt = DateUtils.createNow().getTime();
        this.effectiveDate = startDate;

        effectiveDate.setHours(AppConfig.EFFECTIVE_TIME);
        startDate.setHours(AppConfig.EFFECTIVE_TIME);
        endDate.setHours(AppConfig.EFFECTIVE_TIME);
    }
}
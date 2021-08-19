package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.AppConfig;
import org.thymeleaf.util.DateUtils;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

@Entity
@DiscriminatorValue("CANCELLATION")
public class Cancellation extends Job {
    @PrePersist
    public void PrePersist() {
        this.createAt = DateUtils.createNow().getTime();
        effectiveDate.setHours(AppConfig.EFFECTIVE_TIME);
        startDate.setHours(AppConfig.EFFECTIVE_TIME);
        endDate.setHours(AppConfig.EFFECTIVE_TIME);
    }
}

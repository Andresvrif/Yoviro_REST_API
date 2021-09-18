package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.AppConfig;
import org.thymeleaf.util.DateUtils;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CANCELLATION")
public class Cancellation extends Job {
    @PrePersist
    public void PrePersist() {
        this.createAt = LocalDateTime.now();

        effectiveDate.withHour(AppConfig.EFFECTIVE_TIME);
        startDate.withHour(AppConfig.EFFECTIVE_TIME);
        endDate.withHour(AppConfig.EFFECTIVE_TIME);
    }
}

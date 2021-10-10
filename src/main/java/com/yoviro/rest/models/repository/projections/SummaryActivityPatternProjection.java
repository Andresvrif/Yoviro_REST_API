package com.yoviro.rest.models.repository.projections;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface SummaryActivityPatternProjection {
    Long getId();

    String getPatternCode();

    Boolean getEnable();

    String getSubject();

    LocalDate getStartDate();

    LocalDate getEndDate();

    Integer getDayFrequency();

    LocalTime getHourFrequency();
}
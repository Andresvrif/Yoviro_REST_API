package com.yoviro.rest.models.repository.projections;

import java.util.Date;

public interface SummaryActivityPatternProjection {
    Long getId();
    String getPatternCode();
    Boolean getEnable();
    String getSubject();
    Date getStartDate();
    Date getEndDate();
    String getDayFrequency();
    Date getHourFrequency();
}

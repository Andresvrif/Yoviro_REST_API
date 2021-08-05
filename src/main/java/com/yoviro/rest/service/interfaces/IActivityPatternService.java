package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.ActivityPatternDTO;
import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.models.repository.projections.SummaryActivityPatternProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IActivityPatternService {
    public ActivityPattern edit(ActivityPatternDTO activityPatternDTO);
    public ActivityPattern save(ActivityPatternDTO activityPatternDTO);
    public Page<SummaryActivityPatternProjection> summaryList(Pageable pageable);
}
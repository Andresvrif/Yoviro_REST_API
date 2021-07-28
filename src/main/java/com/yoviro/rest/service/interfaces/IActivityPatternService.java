package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.ActivityPatternDTO;
import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.models.entity.ActivityPattern;

public interface IActivityPatternService {
    public ActivityPattern edit(ActivityPatternDTO activityPatternDTO);
    public ActivityPattern save(ActivityPatternDTO activityPatternDTO);
}
package com.yoviro.rest.dto;

import com.yoviro.rest.config.enums.ActivityStatusEnum;
import com.yoviro.rest.models.entity.User;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ActivityDTO {
    private Long id;

    private Date createAt;

    private ActivityPatternDTO activityPattern;

    private JobDTO job;

    private User assignedUser;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ActivityStatusEnum status;
}
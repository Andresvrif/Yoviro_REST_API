package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoviro.rest.config.enums.ActivityStatusEnum;
import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.models.entity.Job;
import com.yoviro.rest.models.entity.User;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ActivityDTO {
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createAt;

    private ActivityPatternDTO activityPattern;

    private JobDTO job;

    private User assignedUser;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ActivityStatusEnum status;
}
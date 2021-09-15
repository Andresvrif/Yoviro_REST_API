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

    private ActivityStatusEnum status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public ActivityPatternDTO getActivityPattern() {
        return activityPattern;
    }

    public void setActivityPattern(ActivityPatternDTO activityPattern) {
        this.activityPattern = activityPattern;
    }

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public ActivityStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ActivityStatusEnum status) {
        this.status = status;
    }
}
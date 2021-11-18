package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.ActivityStatusEnum;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime createAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_pattern_id")
    private ActivityPattern activityPattern;

    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User assignedUser;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ActivityStatusEnum status;

    @NotNull
    private LocalDateTime assignAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public ActivityPattern getActivityPattern() {
        return activityPattern;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public void setActivityPattern(ActivityPattern activityPattern) {
        this.activityPattern = activityPattern;
    }

    public ActivityStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ActivityStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getAssignAt() {
        return assignAt;
    }

    public void setAssignAt(LocalDateTime assignAt) {
        this.assignAt = assignAt;
    }

    @PrePersist
    public void PrePersist() {
        this.createAt = LocalDateTime.now();

        //Define assign hour
        LocalTime activityPatternHour = activityPattern.getHourFrequency();

        this.assignAt = this.createAt
                .withHour(activityPatternHour.getHour())
                .withMinute(activityPatternHour.getMinute())
                .withSecond(activityPatternHour.getSecond());
    }

    private static final long serialVersionUID = 1L;
}
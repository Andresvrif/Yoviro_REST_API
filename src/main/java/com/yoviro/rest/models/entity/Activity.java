package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.ActivityStatus;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_pattern_id")
    private ActivityPattern activityPattern;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Usuario assignedUser;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

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

    public ActivityPattern getActivityPattern() {
        return activityPattern;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Usuario getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(Usuario assignedUser) {
        this.assignedUser = assignedUser;
    }

    public void setActivityPattern(ActivityPattern activityPattern) {
        this.activityPattern = activityPattern;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    @PrePersist
    public void PrePersist() {
        this.createAt = DateUtils.createNow().getTime();
    }

    private static final long serialVersionUID = 1L;
}

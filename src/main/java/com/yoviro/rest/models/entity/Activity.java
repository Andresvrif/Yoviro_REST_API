package com.yoviro.rest.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

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
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Usuario assignedUser;

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

    public void setActivityPattern(ActivityPattern activityPattern) {
        this.activityPattern = activityPattern;
    }

    private static final long serialVersionUID = 1L;
}

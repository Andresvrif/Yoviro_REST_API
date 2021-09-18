package com.yoviro.rest.models.entity;

import com.yoviro.rest.util.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true)
    private String username;

    @Column(length = 60)
    private String password;

    private Boolean enabled;

    @OneToMany(mappedBy = "assignedUser",
            fetch = FetchType.LAZY)
    private List<Activity> activities = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_team",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id")})
    private List<Team> teams = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Worker worker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    /**
     * Author : Andrés V.
     * Desc : Determines if the activity pattern (hour frequency) can be assign in the workshif of the user
     *
     * @param activityPattern
     * @return
     */
    public Boolean canBeAssigned(LocalDateTime referenceDate,
                                 ActivityPattern activityPattern) {
        //Hasn't been registered as a worker
        Worker worker = this.worker;
        if (worker == null) return Boolean.FALSE;

        //Hasn't a workshift related
        WorkShift workShift = worker.getWorkShift();
        if (workShift == null) return Boolean.FALSE;

        return workShift.isIn(referenceDate, activityPattern);
    }

    /***
     * Author : Andrés V.
     * Desc : Determines if the reference date is inside of the workshift or not
     * @param referenceDate
     * @param workShift
     * @return
     */
    public Boolean isInWorkShift(Date referenceDate,
                                 WorkShift workShift) {
        Calendar referenceCalendar = DateUtil.dateToCalendar(referenceDate);
        LocalDate referenceLocalDate = LocalDate.of(referenceCalendar.get(Calendar.YEAR), referenceCalendar.get(Calendar.MONTH), referenceCalendar.get(Calendar.DAY_OF_MONTH));
        List<WorkshiftItem> workshiftItems = (List<WorkshiftItem>) workShift.getWorkshiftItems().stream().filter(e -> e.getDayOfWeek() == referenceLocalDate.getDayOfWeek());
        return Boolean.FALSE;
    }

    private static final long serialVersionUID = 1L;
}
package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.WorkShiftEnum;
import javax.persistence.*;
import java.io.Serializable;
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
    public Boolean canBeAssigned(ActivityPattern activityPattern) {
        Worker worker = this.worker;
        if(worker == null) return Boolean.FALSE;

        return isInWorkShift(activityPattern.getHourFrequency(), worker.getWorkShiftEnum());
    }

    /**
     * Author : Andrés V.
     * Desc : Determines if the reference date is inside the workshift
     *
     * @param referenceDate
     * @param workshift
     * @return
     */
    private Boolean isInWorkShift(Date referenceDate,
                                  WorkShiftEnum workshift) {
        Date startWorkShiftDate = (Date) referenceDate.clone();
        Date endWorkShiftDate = (Date) referenceDate.clone();
        startWorkShiftDate.setHours(workshift.getStartHour());
        startWorkShiftDate.setMinutes(workshift.getStartMinute());
        endWorkShiftDate.setHours(workshift.getEndHour());
        endWorkShiftDate.setMinutes(workshift.getEndMinute());
        return referenceDate.compareTo(startWorkShiftDate) >= 0 && referenceDate.compareTo(endWorkShiftDate) <= 0;
    }

    private static final long serialVersionUID = 1L;
}
package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.Role;
import com.yoviro.rest.models.entity.Team;
import com.yoviro.rest.models.entity.Worker;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    @JsonIgnore
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private Boolean enabled;

    private List<ActivityDTO> activities = new ArrayList<>();

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
}
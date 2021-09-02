package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.TeamTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private TeamTypeEnum teamType;

    @ManyToMany(mappedBy = "teams")
    private List<User> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TeamTypeEnum getTeamType() {
        return teamType;
    }

    public void setTeamType(TeamTypeEnum teamType) {
        this.teamType = teamType;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    private static final long serialVersionUID = 1L;
}

package com.yoviro.rest.dto;

import com.yoviro.rest.config.enums.TeamTypeEnum;
import com.yoviro.rest.models.entity.User;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class TeamDTO {
    private Long id;
    private TeamTypeEnum teamType;
    private List<UserDTO> users = new ArrayList<>();

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

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.config.enums.TeamTypeEnum;
import com.yoviro.rest.models.entity.Team;
import org.springframework.stereotype.Service;

public interface ITeamService {
    public Team findTeamByTeamType(TeamTypeEnum teamType);
}

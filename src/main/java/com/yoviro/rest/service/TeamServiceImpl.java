package com.yoviro.rest.service;

import com.yoviro.rest.config.enums.TeamTypeEnum;
import com.yoviro.rest.models.entity.Team;
import com.yoviro.rest.models.repository.TeamRepository;
import com.yoviro.rest.service.interfaces.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements ITeamService {
    @Autowired
    TeamRepository teamRepository;

    @Override
    public Team findTeamByTeamType(TeamTypeEnum teamType) {
        return teamRepository.findTeamByTeamType(teamType);
    }
}

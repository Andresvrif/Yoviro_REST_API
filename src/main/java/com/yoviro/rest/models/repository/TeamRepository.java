package com.yoviro.rest.models.repository;

import com.yoviro.rest.config.enums.TeamTypeEnum;
import com.yoviro.rest.models.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    public Team findTeamByTeamType(TeamTypeEnum teamType);
}

package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.ActivityPattern;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ActivityRepository extends PagingAndSortingRepository<Activity, Long> {
    List<Activity> findAllByAssignedUserUsername(String userName);
    List<Activity> findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(Date startDate,
                                                                                  Date endDate,
                                                                                  String userName);

    List<Activity> findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsernameAndActivityPatternPatternCode(Date startDate,
                                                                                                                 Date endDate,
                                                                                                                 String userName,
                                                                                                                 String patternCode);

    List<Activity> findAllByIdIn(List<Long> ids);
}
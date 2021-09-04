package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.models.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IActivityService {
    Iterable<Activity> saveAll(List<Activity> activities);
    List<Activity> findActivitiesAssignedForUserForToday(String userName);
    List<Activity> findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(Date startDate,
                                                                                  Date endDate,
                                                                                  String userName);

}

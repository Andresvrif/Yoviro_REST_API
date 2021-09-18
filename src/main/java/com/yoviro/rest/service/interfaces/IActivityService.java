package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.models.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IActivityService {
    Iterable<Activity> saveAll(List<Activity> activities);
    List<Activity> findActivitiesAssignedForUserForToday(String userName);
    List<Activity> findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(LocalDateTime referenceDate, LocalDateTime endDate, String userName);

    List<Activity> findActivitiesRelatedToPatternCodeAndUserInASpecificDate(LocalDateTime referenceDate, String userName, String patternCode);

    void updateStatusActivities(List<Activity> activities);
}

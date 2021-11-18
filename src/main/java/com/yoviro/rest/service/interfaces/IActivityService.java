package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.models.entity.Activity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IActivityService {
    List<Activity> saveAll(List<Activity> activities);
    List<Activity> findActivitiesAssignedForUserAtDate(String userName, LocalDate referenceDate);
    List<Activity> findActivitiesRelatedToPatternCodeAndUserInASpecificDate(LocalDateTime referenceDate, String userName, String patternCode);
    List<Activity> findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(LocalDateTime startDateTime,
                                                                                  LocalDateTime endDateTime,
                                                                                  String userName);


    void updateStatusActivities(List<Activity> activities);

    Activity findById(Long id);
}
package com.yoviro.rest.service;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.repository.ActivityRepository;
import com.yoviro.rest.service.interfaces.IActivityService;
import com.yoviro.rest.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public Iterable<Activity> saveAll(List<Activity> activities) {
        return activityRepository.saveAll(activities);
    }

    @Override
    public List<Activity> findActivitiesAssignedForUserForToday(String userName) {
        LocalDateTime currentTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        return findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(currentTime, currentTime.plusDays(1), userName);
    }

    @Override
    public List<Activity> findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(LocalDateTime startDate,
                                                                                         LocalDateTime endDate,
                                                                                         String userName) {
        return activityRepository.findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(startDate, endDate, userName);
    }

    @Override
    public List<Activity> findActivitiesRelatedToPatternCodeAndUserInASpecificDate(LocalDateTime referenceDate,
                                                                                   String userName,
                                                                                   String patternCode) {
        LocalDateTime refDate = LocalDateTime.of(referenceDate.toLocalDate(), referenceDate.toLocalTime())
                                                .withMinute(0)
                                                .withSecond(0)
                                                .withNano(0);

        return activityRepository.findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsernameAndActivityPatternPatternCode(refDate,
                refDate.plusDays(1),
                userName,
                patternCode);
    }

    @Override
    public void updateStatusActivities(List<Activity> activities) {
        Activity activityRef;
        List<Long> idActivities = activities.stream().map(activity -> activity.getId()).collect(Collectors.toList());
        List<Activity> activitiesFromDB = activityRepository.findAllByIdIn(idActivities);

        //Update Data
        for (Activity activityToBeUpdated : activitiesFromDB) {
            activityRef = activities.stream().filter(activity -> activity.getId() == activityToBeUpdated.getId()).findFirst().get();
            activityToBeUpdated.setStatus(activityRef.getStatus());
        }

        activityRepository.saveAll(activitiesFromDB);
    }
}

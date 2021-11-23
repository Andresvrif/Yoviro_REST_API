package com.yoviro.rest.service;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.repository.ActivityRepository;
import com.yoviro.rest.service.interfaces.IActivityService;
import com.yoviro.rest.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public List<Activity> saveAll(List<Activity> activities) {
        return (List<Activity>) activityRepository.saveAll(activities);
    }

    @Override
    public List<Activity> findActivitiesAssignedForUserAtDate(String userName,
                                                              LocalDate referenceDate) {
        LocalDateTime currentTime = referenceDate.atStartOfDay();
        return findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(currentTime, currentTime.plusDays(1), userName);
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
    public List<Activity> findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(LocalDateTime startDateTime,
                                                                                         LocalDateTime endDateTime,
                                                                                         String userName) {
        return activityRepository.findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(startDateTime, endDateTime, userName);
    }

    @Transactional
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

        if (!activitiesFromDB.isEmpty()) {
            activityRepository.saveAll(activitiesFromDB);
        }
    }

    @Override
    public Activity findById(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        return optionalActivity.get();
    }

    @Override
    public List<Activity> findAll() {
        return (List<Activity>) activityRepository.findAll();
    }
}
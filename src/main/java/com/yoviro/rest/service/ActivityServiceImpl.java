package com.yoviro.rest.service;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.repository.ActivityRepository;
import com.yoviro.rest.service.interfaces.IActivityService;
import com.yoviro.rest.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Calendar today = DateUtil.dateToCalendar(new Date());
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);

        Calendar tomorrow = (Calendar) today.clone();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);

        return findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(today.getTime(), tomorrow.getTime(), userName);
    }

    @Override
    public List<Activity> findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(Date startDate,
                                                                                         Date endDate,
                                                                                         String userName) {
        return activityRepository.findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(startDate, endDate, userName);
    }

    @Override
    public List<Activity> findActivitiesRelatedToPatternCodeAndUserInASpecificDate(Date referenceDate,
                                                                                   String userName,
                                                                                   String patternCode) {
        Calendar refDateCalendar = DateUtil.dateToCalendar(referenceDate);
        refDateCalendar.set(Calendar.HOUR, 0);
        refDateCalendar.set(Calendar.MINUTE, 0);

        Calendar refTomorrowDateCalendar = (Calendar) refDateCalendar.clone();
        refTomorrowDateCalendar.add(Calendar.DAY_OF_MONTH, 1);

        return activityRepository.findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsernameAndActivityPatternPatternCode(refDateCalendar.getTime(),
                refTomorrowDateCalendar.getTime(),
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

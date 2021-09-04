package com.yoviro.rest.service;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.repository.ActivityRepository;
import com.yoviro.rest.service.interfaces.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
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
        var x = startDate;
        var y = endDate;
        return activityRepository.findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(startDate, endDate, userName);
    }
}

package com.yoviro.rest.service;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.repository.ActivityRepository;
import com.yoviro.rest.service.interfaces.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Date today = new Date();
        today.setHours(0);
        today.setTime(0);
        Date tomorrow = (Date) today.clone();
        tomorrow.setDate(tomorrow.getDay() + 1);

        return findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(today, tomorrow, userName);
    }

    @Override
    public List<Activity> findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(Date startDate,
                                                                                         Date endDate,
                                                                                         String userName) {
        return activityRepository.findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(startDate, endDate, userName);
    }
}

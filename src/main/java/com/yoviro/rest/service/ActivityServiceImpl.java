package com.yoviro.rest.service;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.repository.ActivityRepository;
import com.yoviro.rest.service.interfaces.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public Iterable<Activity> saveAll(List<Activity> activities) {
        return activityRepository.saveAll(activities);
    }
}

package com.yoviro.rest.controller;

import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.service.interfaces.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityRestController {
    @Autowired
    IActivityService activityService;

    @GetMapping("/activitiesForToday")
    public List<Activity> findActivitiesOfUser(@RequestParam(required = true) String userName) {
        System.out.println("userName      -> " + userName);
        var activities = activityService.findActivitiesAssignedForUserForToday(userName);
        System.out.println("----------------------- findActivitiesOfUser - START -----------------------");
        activities.forEach(e -> {
            System.out.println(e +"xxxxxxxxxxxxx");
        });
        System.out.println("----------------------- findActivitiesOfUser -  END  -----------------------");
        return null;
    }
}

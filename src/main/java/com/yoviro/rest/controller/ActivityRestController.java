package com.yoviro.rest.controller;

import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.models.entity.Resident;
import com.yoviro.rest.service.interfaces.IActivityService;
import com.yoviro.rest.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activities")
public class ActivityRestController {
    @Autowired
    IActivityService activityService;

    @GetMapping("/activitiesForToday")
    public Map<String, Object> findActivitiesOfUser(@RequestParam(required = true) String userName) {
        List<Activity> activities = activityService.findActivitiesAssignedForUserForToday(userName);

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("events", wrapActivities(activities));

        return response;
    }

    public List<Map<String, Object>> wrapActivities(List<Activity> activities) {
        Map<String, Object> rowData;
        List<Map<String, Object>> dataContainer = new ArrayList<Map<String, Object>>();
        HashSet<ActivityPattern> activityPatterns = (HashSet<ActivityPattern>) activities.stream().map(e -> e.getActivityPattern()).collect(Collectors.toSet());
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Calendar activityCalendar = null;
        Calendar calendar = DateUtil.dateToCalendar(new Date());
        for (ActivityPattern activityPattern : activityPatterns) {
            activityCalendar = DateUtil.dateToCalendar(activityPattern.getHourFrequency());
            activityCalendar.set(calendar.get(Calendar.YEAR),
                                 calendar.get(Calendar.MONTH),
                                 calendar.get(Calendar.DATE));

            //Put data
            rowData = new HashMap<String, Object>();
            rowData.put("title", activityPattern.getSubject());
            rowData.put("start", pattern.format(activityCalendar.getTime()));
            rowData.put("patternCode", activityPattern.getPatternCode());

            dataContainer.add(rowData);
        }

        return dataContainer;
    }
}

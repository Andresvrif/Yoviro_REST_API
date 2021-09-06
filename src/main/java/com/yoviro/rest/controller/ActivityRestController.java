package com.yoviro.rest.controller;

import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.service.interfaces.IActivityService;
import com.yoviro.rest.util.DateUtil;
import com.yoviro.rest.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
        response.put("events", wrapActivityEvents(activities));

        return response;
    }

    /***
     * Author: Andrés V.
     * Desc : Wrap events associated to the user
     * @param activities
     * @return
     */
    public List<Map<String, Object>> wrapActivityEvents(List<Activity> activities) {
        Map<String, Object> rowData;
        List<Map<String, Object>> dataContainer = new ArrayList<Map<String, Object>>();
        HashSet<ActivityPattern> activityPatterns = (HashSet<ActivityPattern>) activities.stream().map(e -> e.getActivityPattern()).collect(Collectors.toSet());
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Calendar activityCalendar = null;
        Calendar calendar = DateUtil.dateToCalendar(new Date());
        for (ActivityPattern activityPattern : activityPatterns) {
            activityCalendar = DateUtil.dateToCalendar(activityPattern.getHourFrequency());
            activityCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

            //Put data
            rowData = new HashMap<String, Object>();
            rowData.put("title", activityPattern.getSubject());
            rowData.put("start", pattern.format(activityCalendar.getTime()));
            rowData.put("patternCode", activityPattern.getPatternCode());
            rowData.put("color", activityPattern.getColorCode());

            dataContainer.add(rowData);
        }

        return dataContainer;
    }

    @GetMapping("/activityDetailResidents")
    public Map<String, Object> activityDetailResidents(@RequestParam String userName,
                                                       @RequestParam String patternCode,
                                                       @RequestParam Date assignedDate) {
        var activities = activityService.findActivitiesRelatedToPatternCodeAndUserInASpecificDate(assignedDate, userName, patternCode);
        //Define Response
        return wrapActivityDetailResidents(activities);
    }

    public Map<String, Object> wrapActivityDetailResidents(List<Activity> activities) {
        if (activities == null) return null;
        if (activities.isEmpty()) return null;

        ActivityPattern activityPatternReference = activities.stream().findFirst().get().getActivityPattern();
        Map<String, Object> activityProperties = new HashMap<>();
        activityProperties.put("patternCode", activityPatternReference.getPatternCode());
        activityProperties.put("residents", wrapResidents(activities));


        Map<String, Object> data = new HashMap<>();
        data.put("Activity", activityProperties);

        return data;
    }

    private List<Map<String, Object>> wrapResidents(List<Activity> activities) {
        Job job;
        Resident resident;
        Contact contact;
        OfficialId primaryOfficialID;
        Map<String, Object> rowData;
        Map<String, String> officialID;
        List<Map<String, Object>> dataContainer = new ArrayList<Map<String, Object>>();

        for (Activity activity : activities) {
            job = activity.getJob();
            resident = job.getResident();
            contact = resident.getContact();
            primaryOfficialID = contact.getPrimaryOfficialID();

            //Put data
            officialID = new HashMap<>();
            officialID.put("officialIDType", primaryOfficialID.getOfficialIdType().toString());
            officialID.put("officialIDNumber", primaryOfficialID.getOfficialIdNumber());

            rowData = new HashMap<String, Object>();
            rowData.put("fullName", StringUtil.capitalizeWord(contact.getFullName()));
            rowData.put("officialId", officialID);
            rowData.put("status", activity.getStatus());

            dataContainer.add(rowData);
        }

        return dataContainer;
    }
}
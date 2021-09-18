package com.yoviro.rest.controller;

import com.yoviro.rest.config.enums.ActivityStatusEnum;
import com.yoviro.rest.dto.ActivityDTO;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.service.interfaces.IActivityService;
import com.yoviro.rest.util.DateUtil;
import com.yoviro.rest.util.StringUtil;
import org.apache.tomcat.jni.Local;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime localDateTime;
        for (ActivityPattern activityPattern : activityPatterns) {
            LocalTime activityPatternHour = activityPattern.getHourFrequency();
            localDateTime = LocalDateTime.now()
                                         .withHour(activityPatternHour.getHour())
                                         .withMinute(activityPatternHour.getMinute())
                                         .withSecond(activityPatternHour.getSecond());

            //Put data
            rowData = new HashMap<String, Object>();
            rowData.put("title", activityPattern.getSubject());
            rowData.put("start", localDateTime.format(formatter));
            rowData.put("patternCode", activityPattern.getPatternCode());
            rowData.put("color", activityPattern.getColorCode());

            dataContainer.add(rowData);
        }

        return dataContainer;
    }

    @GetMapping("/activityDetailResidents")
    public Map<String, Object> activityDetailResidents(@RequestParam String userName,
                                                       @RequestParam String patternCode,
                                                       @RequestParam LocalDate assignedDate) {
        LocalDateTime localDateTime = LocalDateTime.of(assignedDate, LocalTime.MIDNIGHT);
        var activities = activityService.findActivitiesRelatedToPatternCodeAndUserInASpecificDate(localDateTime, userName, patternCode);

        //Define Response
        return wrapActivities(activities);
    }

    /***
     * Author: Andrés V.
     * Desc : Wrap activities
     * @param activities
     * @return
     */
    public Map<String, Object> wrapActivities(List<Activity> activities) {
        if (activities == null) return null;
        if (activities.isEmpty()) return null;

        Job job;
        Resident resident;
        Contact contact;
        OfficialId primaryOfficialID;
        List<Map<String, Object>> wrapActivities = new ArrayList<>();
        Map<String, Object> rowData;

        for (Activity activity : activities) {
            job = activity.getJob();
            resident = job.getResident();
            contact = resident.getContact();
            primaryOfficialID = contact.getPrimaryOfficialID();
            rowData = new HashMap<String, Object>();
            rowData.put("id", activity.getId());
            rowData.put("fullName", StringUtil.capitalizeWord(contact.getFullName()));
            rowData.put("officialIdNumber", primaryOfficialID.getOfficialIdNumber());
            rowData.put("officialIdType", primaryOfficialID.getOfficialIdType());
            rowData.put("status", activity.getStatus());
            rowData.put("relatedForm", activity.getActivityPattern().getRelatedForm());

            wrapActivities.add(rowData);
        }


        Map<String, Object> data = new HashMap<>();
        data.put("Activities", wrapActivities);

        return data;
    }

    @PutMapping("/updateStatusActivities")
    public void updateStatusActivities(@RequestBody String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject jsonActivity;
        JSONArray jsonActivities = jsonObject.getJSONArray("activities");

        Activity activity;
        List<Activity> activities = new ArrayList<>();
        for (int i = 0; i < jsonActivities.length(); i++) {
            jsonActivity = jsonActivities.getJSONObject(i);

            //Instance Activity
            activity = new Activity();
            activity.setId(Long.valueOf(jsonActivity.get("id").toString()));
            activity.setStatus(ActivityStatusEnum.valueOf(jsonActivity.get("status").toString()));

            activities.add(activity);
        }

        activityService.updateStatusActivities(activities);
    }
}
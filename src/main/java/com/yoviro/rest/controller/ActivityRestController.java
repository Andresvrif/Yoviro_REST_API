package com.yoviro.rest.controller;

import com.yoviro.rest.config.enums.ActivityStatusEnum;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.security.service.IJWTService;
import com.yoviro.rest.service.interfaces.IActivityService;
import com.yoviro.rest.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private IJWTService jwtService;

    @Autowired
    IActivityService activityService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/myActivities")
    public Map<String, Object> myActivities(@RequestHeader(name = "Authorization") String authorization,
                                            @RequestParam LocalDate startDate,
                                            @RequestParam LocalDate endDate) {
        //Retrieve userName from token
        String token = jwtService.retrieveToken(authorization);
        String userName = jwtService.getUserName(token);

        //Stablish time
        LocalDateTime startDateTime = startDate.atStartOfDay().minusSeconds(1);
        LocalDateTime endDateTime = endDate.atStartOfDay();
        List<Activity> activities = activityService.findAllByCreateAtAfterAndCreateAtBeforeAndAssignedUserUsername(startDateTime, endDateTime, userName);

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

        //Map activities by day
        HashMap<LocalDate, List<Activity>> activitiesByDay = new HashMap<>();
        HashSet<LocalDate> daysWithActivities = (HashSet<LocalDate>) activities.stream().map(e -> e.getAssignAt().toLocalDate()).collect(Collectors.toSet());
        daysWithActivities.forEach(day -> activitiesByDay.put(day, activities.stream().filter(e -> e.getAssignAt().toLocalDate().compareTo(day) == 0).collect(Collectors.toList())));

        //Create activities for UI
        LocalDateTime referenceDateTime;
        HashSet<ActivityPattern> activityPatternsByDay;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (LocalDate day : activitiesByDay.keySet()) {
            activityPatternsByDay = (HashSet<ActivityPattern>) activitiesByDay.get(day).stream().map(e -> e.getActivityPattern()).collect(Collectors.toSet());
            for (ActivityPattern pattern : activityPatternsByDay) {
                //Define date
                referenceDateTime = day.atTime(pattern.getHourFrequency());

                //Put data
                rowData = new HashMap<String, Object>();
                rowData.put("title", pattern.getSubject());
                rowData.put("start", referenceDateTime.format(formatter));
                rowData.put("patternCode", pattern.getPatternCode());
                rowData.put("color", pattern.getColorCode());

                dataContainer.add(rowData);
            }
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
        Person person;
        OfficialId primaryOfficialID;
        List<Map<String, Object>> wrapActivities = new ArrayList<>();
        Map<String, Object> rowData;

        for (Activity activity : activities) {
            job = activity.getJob();
            resident = job.getResident();
            person = resident.getPerson();
            primaryOfficialID = person.getPrimaryOfficialID();
            rowData = new HashMap<String, Object>();
            rowData.put("id", activity.getId());
            rowData.put("fullName", StringUtil.capitalizeWord(person.getFullName()));
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

    @GetMapping("/summaryList")
    public List<Map<String, Object>> summaryList() {
        return wrapSummaryList(activityService.findAll());
    }

    public List<Map<String, Object>> wrapSummaryList(List<Activity> activities) {
        List<Map<String, Object>> wrapActivities = new ArrayList<>();
        Map<String, Object> rowData;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Activity activity : activities) {
            rowData = new HashMap<String, Object>();
            rowData.put("userName", activity.getAssignedUser().getUsername());
            rowData.put("assignAt", activity.getAssignAt().format(formatter));
            rowData.put("status", activity.getStatus());
            rowData.put("activityPattern", activity.getActivityPattern().getPatternCode());
            rowData.put("resident", activity.getJob().getResident().getPerson().getFullName());

            wrapActivities.add(rowData);
        }

        return wrapActivities;
    }
}
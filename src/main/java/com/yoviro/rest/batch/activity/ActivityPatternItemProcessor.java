package com.yoviro.rest.batch.activity;

import com.yoviro.rest.config.enums.ActivityStatusEnum;
import com.yoviro.rest.config.enums.TeamTypeEnum;
import com.yoviro.rest.handler.JobHandler;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.service.interfaces.ITeamService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityPatternItemProcessor implements ItemProcessor<ActivityPattern, List<Activity>> {
    private static TeamTypeEnum teamTypeEnum = TeamTypeEnum.NURSES;

    @Autowired
    private ITeamService teamService;

    @Override
    public List<Activity> process(ActivityPattern activityPattern) throws Exception {
        return processActivityPattern(activityPattern);
    }

    public List<Activity> processActivityPattern(ActivityPattern activityPattern) throws Exception {
        var team = teamService.findTeamByTeamType(teamTypeEnum);
        List<User> users = retrieveUserTeams(team);
        List<Activity> activitiesToBeCreated = new ArrayList<Activity>();
        List<Agreement> agreements = activityPattern.getAgreements();
        LocalDateTime currentDate = LocalDateTime.now();
        HashMap<User, List<Activity>> userDistribution = defineUserDistribution(users);

        if (agreements.isEmpty()) return null;
        for (Agreement agreement : agreements) {
            if (!agreement.applyActivityPatternToBeAssigned(currentDate, activityPattern)) continue;

            //Define User to be assigned
            User userToAssign = defineUserToBeAssigned(currentDate,
                    userDistribution,
                    activityPattern);

            //At this point, the activity can be created
            Activity activity = new Activity();
            activity.setActivityPattern(activityPattern);
            activity.setJob(JobHandler.lastJobFromAgreement(agreement));
            activity.setStatus(ActivityStatusEnum.DEFINED);
            activity.setAssignedUser(userToAssign);

            activitiesToBeCreated.add(activity);

            userDistribution.get(userToAssign).add(activity);
        }

        return activitiesToBeCreated.isEmpty() ? null : activitiesToBeCreated;
    }

    /***
     * Author : Andrés V.
     * Desc :
     * @param team
     * @return
     */
    private List<User> retrieveUserTeams(Team team) {
        List<User> usersTeam = team.getUsers();
        List<User> candidates = new ArrayList<User>();

        for (User user : usersTeam) {
            if (user.getWorker() == null) continue;

            candidates.add(user);
        }

        return candidates;
    }

    /***
     * Author : Andrés V.
     * Desc : Instan map to represent distribution of activities
     * @param users
     * @return
     */
    private HashMap<User, List<Activity>> defineUserDistribution(List<User> users) {
        HashMap<User, List<Activity>> activityDistribution = new HashMap<User, List<Activity>>();
        for (User user : users) {
            activityDistribution.put(user, new ArrayList<Activity>());
        }
        return activityDistribution;
    }

    /***
     * Author : Andrés V.
     * Desc : Defines the user to be assigned accord user and activity pattern
     * @param users
     * @param activityPattern
     * @return
     */
    private User defineUserToBeAssigned(LocalDateTime referenceDate,
                                        HashMap<User, List<Activity>> userDistribution,
                                        ActivityPattern activityPattern) {
        List<User> users = userDistribution.keySet().stream().filter(e -> e.canBeAssigned(referenceDate, activityPattern)).collect(Collectors.toList());
        User userToBeAssigned = null;
        for (User user : users) {
            if (userToBeAssigned == null) userToBeAssigned = user;

            if (userDistribution.get(userToBeAssigned).size() > userDistribution.get(user).size()) {
                userToBeAssigned = user;
            }
        }

        return userToBeAssigned;
    }
}
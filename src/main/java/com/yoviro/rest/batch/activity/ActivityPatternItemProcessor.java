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

        for (Agreement agreement : agreements) {
            if (!agreement.applyActivityPatternToBeAssigned(currentDate, activityPattern)) continue;

            //Define User to be assigned
            User userToAssign = defineUserToBeAssigned(currentDate,
                                                       users,
                                                       activityPattern);

            //At this point, the activity can be created
            Activity activity = new Activity();
            activity.setActivityPattern(activityPattern);
            activity.setJob(JobHandler.lastJobFromAgreement(agreement));
            activity.setStatus(ActivityStatusEnum.DEFINED);
            activity.setAssignedUser(userToAssign);

            activitiesToBeCreated.add(activity);
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
     * Desc : Defines the user to be assigned accord user and activity pattern
     * @param users
     * @param activityPattern
     * @return
     */
    private User defineUserToBeAssigned(LocalDateTime referenceDate,
                                        List<User> users,
                                        ActivityPattern activityPattern) {
        users = users.stream().filter(e -> e.canBeAssigned(referenceDate, activityPattern)).collect(Collectors.toList());
        //TODO BALANCING!
        User userToBeAssigned = null;
        for (User user : users) {
            if (userToBeAssigned == null) userToBeAssigned = user;

            if (userToBeAssigned.getActivities().size() < user.getActivities().size()) {
                userToBeAssigned = user;
            }
        }

        return userToBeAssigned;
    }
}
package com.yoviro.rest.batch.activity;

import com.yoviro.rest.config.enums.ActivityStatusEnum;
import com.yoviro.rest.config.enums.StatusTerm;
import com.yoviro.rest.config.enums.TeamTypeEnum;
import com.yoviro.rest.handler.JobHandler;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import com.yoviro.rest.service.interfaces.ITeamService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityPatternItemProcessor implements ItemProcessor<ActivityPattern, List<Activity>> {
    private static TeamTypeEnum teamTypeEnum = TeamTypeEnum.NURSES;

    @Autowired
    private ITeamService teamService;

    @Override
    public List<Activity> process(ActivityPattern activityPattern) throws Exception {
        System.out.println("------------------------------ process - START ------------------------------");


        return processActivityPattern(activityPattern);
    }

    public List<Activity> processActivityPattern(ActivityPattern activityPattern) throws Exception {
        var team = teamService.findTeamByTeamType(teamTypeEnum);
        List<User> users = retrieveUserTeams(team);
        List<Activity> activitiesToBeCreated = new ArrayList<Activity>();
        List<Agreement> agreements = activityPattern.getAgreements();

        for (Agreement agreement : agreements) {
            if (!applyToBeCreated(activityPattern, agreement)) continue;
            //Define User to be assigned
            User userToAssign = defineUserToBeAssigned(users, activityPattern);

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
     * Desc : Evaluates if the activity can be created or not
     * @param activityPattern
     * @param agreement
     * @return
     */
    private Boolean applyToBeCreated(ActivityPattern activityPattern,
                                     Agreement agreement) {
        Date currentDate = new Date();
        try {
            //Can't create activities in not vigent agreements
            if (agreement.getStatus(currentDate) != StatusTerm.VIGENT)
                return Boolean.FALSE;

            //Can't re create the same activity in the same date
            if (agreement.getJobs().stream().anyMatch(job -> JobHandler.hasJobActivityRelated(job, activityPattern, currentDate)))
                return Boolean.FALSE;

            return Boolean.TRUE;
        } catch (Exception ex) {
            return Boolean.FALSE;
        }
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

    private User defineUserToBeAssigned(List<User> users,
                                        ActivityPattern activityPattern) {
        List<User> candidates = new ArrayList<>();
        for (User userToBeEvaluated : users) {

        }
        return null;
    }
}
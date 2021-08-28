package com.yoviro.rest.batch.activity;

import com.yoviro.rest.config.enums.ActivityStatus;
import com.yoviro.rest.config.enums.StatusTerm;
import com.yoviro.rest.handler.JobHandler;
import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Job;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ActivityPatternItemProcessor implements ItemProcessor<ActivityPattern, List<Activity>> {
    @Override
    public List<Activity> process(ActivityPattern activityPattern) throws Exception {
        return processActivityPattern(activityPattern);
    }

    public List<Activity> processActivityPattern(ActivityPattern activityPattern) throws Exception {
        List<Activity> toBeCreated = new ArrayList<Activity>();
        List<Agreement> agreements = activityPattern.getAgreements();
        for (Agreement agreement : agreements) {
            if (!applyToBeCreated(activityPattern, agreement)) continue;

            //At this point, the activity can be created
            Activity activity = new Activity();
            activity.setActivityPattern(activityPattern);
            activity.setJob(JobHandler.lastJobFromAgreement(agreement));
            activity.setStatus(ActivityStatus.DEFINED);

            toBeCreated.add(activity);
        }

        return toBeCreated.isEmpty() ? null : toBeCreated;
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
}
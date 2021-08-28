package com.yoviro.rest.handler;

import com.yoviro.rest.config.enums.StatusTerm;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.util.DateUtil;

import java.util.*;

public class JobHandler {
    public static Job lastJobFromAgreement(Agreement agreement) {
        List<Job> jobs = agreement.getJobs();
        if (jobs.isEmpty()) return null;

        jobs.sort(Comparator.comparing(Job::getId));
        return jobs.get(jobs.size() - 1);
    }

    /***
     * Author : Andrés V.
     * Desc : This is a business rule, cancellation can only be executed with a agreement live term
     * @param agreement
     * @param effectiveDate
     * @return
     */
    public static Boolean canBeCanceled(Agreement agreement,
                                        Date effectiveDate) {
        Job lastJob = lastJobFromAgreement(agreement);
        return !(lastJob instanceof Cancellation) && (DateUtil.compareIgnoreTime(effectiveDate, lastJob.getEndDate()) <= 0 && DateUtil.compareIgnoreTime(effectiveDate, lastJob.getStartDate()) >= 0);
    }

    /***
     * Author : Andrés V.
     * Desc : Returns agreement status based in the job
     * @param job
     * @param referenceDate
     * @return
     */
    public static StatusTerm getStatusTerm(Job job,
                                           Date referenceDate) {
        Date startDate = job.getStartDate();
        Date endDate = job.getEndDate();
        Date effectiveDate = job.getEffectiveDate();
        if (job instanceof Submission) {
            Submission submission = (Submission) job;
            if (DateUtil.compareIgnoreTime(referenceDate, endDate) > 0) {
                //AFTER TERM
                return StatusTerm.NO_VIGENT;
            } else if (DateUtil.compareIgnoreTime(referenceDate, startDate) < 0) {
                //BEFORE TERM
                return StatusTerm.PLANNED;
            } else if (DateUtil.compareIgnoreTime(startDate, referenceDate) <= 0 &&
                    DateUtil.compareIgnoreTime(referenceDate, endDate) <= 0) {
                return StatusTerm.VIGENT;
            }
            return null;
        } else if (job instanceof Cancellation) {
            Cancellation cancellation = (Cancellation) job;
            if (DateUtil.compareIgnoreTime(referenceDate, endDate) > 0) {
                //AFTER TERM
                return StatusTerm.NO_VIGENT;
            } else if (DateUtil.compareIgnoreTime(referenceDate, startDate) < 0) {
                //BEFORE TERM
                return StatusTerm.CANCELLED;
            } else if (DateUtil.compareIgnoreTime(startDate, referenceDate) <= 0 &&
                    DateUtil.compareIgnoreTime(referenceDate, endDate) <= 0) {
                //INSIDE TERM
                //INSIDE ENABLE PERIOD AFTER CANCELLATION
                if (DateUtil.compareIgnoreTime(referenceDate, effectiveDate) <= 0) {
                    return StatusTerm.VIGENT;
                } else {
                    return StatusTerm.CANCELLED;
                }
            }

            return null;
        } else {
            return null;
        }
    }

    /***
     * Author : Andrés V.
     * Desc : Returns true, if the job has an activity created in the referenceDate with the same activity pattern
     * @param job
     * @param activityPattern
     * @return
     */
    public static Boolean hasJobActivityRelated(Job job,
                                                ActivityPattern activityPattern,
                                                Date refDate) {
        return job.getActivities().stream().anyMatch(e -> e.getActivityPattern().getPatternCode() == activityPattern.getPatternCode() && DateUtil.compareIgnoreTime(e.getCreateAt(), refDate) == 0);
    }
}
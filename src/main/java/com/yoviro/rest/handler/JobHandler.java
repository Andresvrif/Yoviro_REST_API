package com.yoviro.rest.handler;

import com.yoviro.rest.config.enums.StatusTermEnum;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.util.DateUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class JobHandler {
    public static Job lastJobFromAgreement(Agreement agreement) {
        List<Job> jobs = agreement.getJobs().stream().collect(Collectors.toList());
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
                                        LocalDateTime effectiveDate) {
        Job lastJob = lastJobFromAgreement(agreement);
        return !(lastJob instanceof Cancellation) && (effectiveDate.compareTo(lastJob.getEndDate()) <= 0 && effectiveDate.compareTo(lastJob.getStartDate()) >= 0);
    }

    /***
     * Author : Andrés V.
     * Desc : Returns agreement status based in the job
     * @param job
     * @param referenceDate
     * @return
     */
    public static StatusTermEnum getStatusTerm(Job job,
                                               LocalDateTime referenceDate) {
        LocalDateTime startDate = job.getStartDate();
        LocalDateTime endDate = job.getEndDate();
        LocalDateTime effectiveDate = job.getEffectiveDate();
        if (job instanceof Submission) {
            if (endDate == null) {
                return referenceDate.compareTo(startDate) < 0 ? StatusTermEnum.PLANNED : StatusTermEnum.VIGENT;
            } else {
                if (referenceDate.compareTo(endDate) > 0) {
                    //AFTER TERM
                    return StatusTermEnum.NO_VIGENT;
                } else if (referenceDate.compareTo(startDate) < 0) {
                    //BEFORE TERM
                    return StatusTermEnum.PLANNED;
                } else if (startDate.compareTo(referenceDate) <= 0 && referenceDate.compareTo(endDate) <= 0) {
                    return StatusTermEnum.VIGENT;
                }
            }
            return null;
        } else if (job instanceof Cancellation) {
            if (referenceDate.compareTo(endDate) > 0) {
                //AFTER TERM
                return StatusTermEnum.NO_VIGENT;
            } else if (referenceDate.compareTo(startDate) < 0) {
                //BEFORE TERM
                return StatusTermEnum.CANCELLED;
            } else if (startDate.compareTo(referenceDate) <= 0 && referenceDate.compareTo(endDate) <= 0) {
                //INSIDE TERM
                //INSIDE ENABLE PERIOD AFTER CANCELLATION
                if (referenceDate.compareTo(effectiveDate) <= 0) {
                    return StatusTermEnum.VIGENT;
                } else {
                    return StatusTermEnum.CANCELLED;
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
                                                LocalDateTime refDate) {
        return job.getActivities().stream().anyMatch(e -> e.getActivityPattern().getPatternCode() == activityPattern.getPatternCode() && DateUtil.compareIgnoreTime(e.getCreateAt(), refDate) == 0);
    }
}
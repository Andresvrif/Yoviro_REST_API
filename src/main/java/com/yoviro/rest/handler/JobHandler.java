package com.yoviro.rest.handler;

import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Cancellation;
import com.yoviro.rest.models.entity.Job;
import com.yoviro.rest.util.DateUtil;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JobHandler {
    public Job lastJobFromAgreement(Agreement agreement) {
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
    public Boolean canBeCanceled(Agreement agreement,
                                 Date effectiveDate) {
        Job lastJob = lastJobFromAgreement(agreement);
        return !(lastJob instanceof Cancellation) && (
                DateUtil.compareIgnoreTime(effectiveDate, lastJob.getEndDate()) <=0 &&
                DateUtil.compareIgnoreTime(effectiveDate, lastJob.getStartDate()) >=0);
    }
}
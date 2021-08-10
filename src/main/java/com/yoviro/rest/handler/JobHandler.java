package com.yoviro.rest.handler;

import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Job;

import java.util.*;

public class JobHandler {
    public Job lastJob(Agreement agreement, Date effectiveDate) {
        List<Job> jobs = agreement.getJobs();
        if (jobs.isEmpty()) return null;

        jobs.sort(Comparator.comparing(Job::getId));
        return jobs.get(jobs.size() - 1);
    }
}
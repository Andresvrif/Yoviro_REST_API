package com.yoviro.rest.handler;

import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Job;

import java.util.List;

public class JobHandler {
    public Job lastJob(Agreement agreement) {
        List<Job> jobs = agreement.getJobs();
        //var x = jobs.stream().sorted(j ->);
        return null;
    }

}
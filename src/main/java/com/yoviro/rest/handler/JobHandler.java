package com.yoviro.rest.handler;

import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Job;

import java.util.List;

public class JobHandler {
    public Boolean enableToCancel(Agreement agreement) {
        List<Job> jobs = agreement.getJobs();
        return null;
    }
}
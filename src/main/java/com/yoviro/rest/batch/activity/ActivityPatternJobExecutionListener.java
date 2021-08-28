package com.yoviro.rest.batch.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class ActivityPatternJobExecutionListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityPatternJobExecutionListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        //LOGGER.info("beforeJob");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        //LOGGER.info("afterJob");
    }
}

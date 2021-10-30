package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.service.interfaces.IActivityService;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.batch.runtime.context.StepContext;
import java.util.Iterator;
import java.util.List;

public class ActivityPatternItemWriter implements ItemWriter<List<Activity>> {
    //private int commitedCounter = 0;

    @Autowired
    IActivityService activityService;

    @Override
    public void write(List<? extends List<Activity>> list) throws Exception {
        List<Activity> savedActivities;
        for (List<Activity> activitiesToBeCommited : list) {
            savedActivities = activityService.saveAll(activitiesToBeCommited);
            //commitedCounter += savedActivities.size();
        }
    }

    @AfterStep
    public void after(StepExecution stepExecution) {
        //stepExecution.setWriteCount(commitedCounter);
    }
}
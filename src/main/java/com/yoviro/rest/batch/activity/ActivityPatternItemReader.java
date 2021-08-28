package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

public class ActivityPatternItemReader implements ItemReader<ActivityPattern> {

    @Autowired
    private IActivityPatternService activityPatternService;

    private Iterator<ActivityPattern> activityPatternIterator;

    @BeforeStep
    public void before(StepExecution stepExecution) {
        activityPatternIterator = activityPatternService.findAllByEnable(Boolean.TRUE).iterator();
    }

    @Override
    public ActivityPattern read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return processActivityPattern(activityPatternIterator);
    }

    public ActivityPattern processActivityPattern(Iterator<ActivityPattern> iterator) {
        while (iterator.hasNext()) {
            ActivityPattern candidate = activityPatternIterator.next();
            return applyActivityPattern(candidate) ? candidate : processActivityPattern(iterator);
        }
        return null;
    }

    /**
     * Author : Andrés V.
     * Desc : Evaluates if the activity Pattern applies or not
     *
     * @param candidate
     * @return
     */
    private Boolean applyActivityPattern(ActivityPattern candidate) {
        if(!candidate.hasAgreements()) return Boolean.FALSE;
        return Boolean.TRUE;
    }
}
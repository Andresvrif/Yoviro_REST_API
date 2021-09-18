package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import com.yoviro.rest.util.DateUtil;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;

public class ActivityPatternItemReader implements ItemReader<ActivityPattern> {
    Date currentDate = new Date();

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

    /**
     * Author : Andrés V.
     * Desc : Defines if the activity pattern applies to be created
     * @param iterator
     * @return
     */
    public ActivityPattern processActivityPattern(Iterator<ActivityPattern> iterator) {
        LocalDateTime currentDate = LocalDateTime.now();
        while (iterator.hasNext()) {
            ActivityPattern candidate = activityPatternIterator.next();
            return applyActivityPattern(currentDate, candidate) ? candidate : processActivityPattern(iterator);
        }
        return null;
    }

    /**
     * Author : Andrés V.
     * Desc : Evaluates if the activity Pattern applies or not
     *
     * @param
     * @return
     */
    private Boolean applyActivityPattern(LocalDateTime referenceDate,
                                         ActivityPattern activityPattern) {
        if (!activityPattern.hasAgreements()) return Boolean.FALSE; //Check if has agreements
        if (!applySchedule(referenceDate, activityPattern)) return Boolean.FALSE; //Check if the current date applies

        return Boolean.TRUE;
    }

    /***
     * Author : Andrés V.
     * Desc :  Determines if the activity pattern FREQUENCY should be created in the current date
     * @param activityPattern
     * @return
     */
    private Boolean applySchedule(LocalDateTime referenceDate,
                                  ActivityPattern activityPattern) {
        return DateUtil.insideOfRange(referenceDate,
                activityPattern.getStartDate(),
                activityPattern.getEndDate(),
                activityPattern.getDayFrequency());
    }
}
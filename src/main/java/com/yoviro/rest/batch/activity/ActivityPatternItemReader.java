package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import com.yoviro.rest.util.DateUtil;
import org.hibernate.Hibernate;
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
import java.util.List;

public class ActivityPatternItemReader implements ItemReader<ActivityPattern> {
    Date currentDate = new Date();

    @Autowired
    private IActivityPatternService activityPatternService;

    private Iterator<ActivityPattern> activityPatternIterator;

    @BeforeStep
    public void before(StepExecution stepExecution) {
        List<ActivityPattern> activityPatterns = activityPatternService.bringCandidatesToCreateActivities();
        System.out.println("----------------------- START - before -----------------------");
        for (ActivityPattern activityPattern : activityPatterns) {
            System.out.println("\tPattern Code : " + activityPattern.getPatternCode() + "\n" +
                    "\t\tAgreement Size : " + activityPattern.getAgreements().size() + "\n" +
                    "\t\t\tJob Size : " + activityPattern.getAgreements().stream().findFirst().get().getJobs().size() + "\n"
            );
        }
        System.out.println("-----------------------  END  - before -----------------------");
        activityPatternIterator = activityPatterns.iterator();
    }

    @Override
    public ActivityPattern read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return processActivityPattern(activityPatternIterator);
    }

    /**
     * Author : Andrés V.
     * Desc : Defines if the activity pattern applies to be created
     *
     * @param iterator
     * @return
     */
    public ActivityPattern processActivityPattern(Iterator<ActivityPattern> iterator) {
        LocalDateTime currentDate = LocalDateTime.now();
        while (iterator.hasNext()) {
            ActivityPattern candidate = iterator.next();
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
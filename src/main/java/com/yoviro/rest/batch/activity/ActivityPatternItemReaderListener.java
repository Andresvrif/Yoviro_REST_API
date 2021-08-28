package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.ActivityPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class ActivityPatternItemReaderListener implements ItemReadListener<ActivityPattern> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityPatternItemReaderListener.class);

    @Override
    public void beforeRead() {
        //LOGGER.info("beforeRead");
    }

    @Override
    public void afterRead(ActivityPattern activityPattern) {
        LOGGER.info("The Activity Pattern : " + activityPattern.getPatternCode() + ", is enable to be created");
    }

    @Override
    public void onReadError(Exception e) {
        //LOGGER.info("onReadError");
    }
}

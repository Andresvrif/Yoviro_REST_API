package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.ActivityPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;

import java.util.List;

public class ActivityPatternItemProcessListener implements ItemProcessListener<ActivityPattern, List<Activity>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityPatternItemProcessListener.class);

    @Override
    public void beforeProcess(ActivityPattern activityPattern) {
    }

    @Override
    public void afterProcess(ActivityPattern activityPattern, List<Activity> activities) {
        int activitiesSize = (activities == null || activities.isEmpty()) ? 0 : activities.size();
        if (activitiesSize == 0) {
            LOGGER.info("No activities has been created for ActivityPattern : " + activityPattern.getPatternCode());
        } else {
            LOGGER.info("Has been created " + activities.size() + " activities, for ActivityPattern : " + activityPattern.getPatternCode());
        }
    }

    @Override
    public void onProcessError(ActivityPattern activityPattern, Exception e) {
        //LOGGER.info("onProcessError");
    }
}

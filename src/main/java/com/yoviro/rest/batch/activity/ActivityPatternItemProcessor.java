package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.ActivityPattern;
import org.springframework.batch.item.ItemProcessor;

public class ActivityPatternItemProcessor implements ItemProcessor<ActivityPattern, Activity> {

    @Override
    public Activity process(ActivityPattern activityPattern) throws Exception {
        Activity activity = new Activity();
        activity.setActivityPattern(activityPattern);
        System.out.println("\tActividad creada para el activity pattern : " + activityPattern.getPatternCode());

        return activity;
    }
}

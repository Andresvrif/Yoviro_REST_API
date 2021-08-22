package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.ActivityPattern;
import org.springframework.batch.item.ItemProcessor;

public class ActivityItemProcessor implements ItemProcessor<ActivityPattern, Activity> {
    @Override
    public Activity process(ActivityPattern activityPattern) throws Exception {
        Activity activity = new Activity();
        activity.setActivityPattern(activityPattern);

        System.out.println("Se creo la actividad del Activity Pattern : " + activityPattern.getPatternCode());
        return activity;
    }
}

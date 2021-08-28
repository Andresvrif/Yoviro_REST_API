package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.service.interfaces.IActivityService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class ActivityPatternItemWriter implements ItemWriter<List<Activity>> {
    @Autowired
    IActivityService activityService;

    @Override
    public void write(List<? extends List<Activity>> list) throws Exception {
        list.forEach(subList -> activityService.saveAll(subList));
    }
}
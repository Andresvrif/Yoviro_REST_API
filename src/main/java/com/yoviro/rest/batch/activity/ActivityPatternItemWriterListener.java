package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.ActivityPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ActivityPatternItemWriterListener implements ItemWriter<Activity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityPatternItemWriterListener.class);

    @Override
    public void write(List<? extends Activity> list) throws Exception {

    }
}
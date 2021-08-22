package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.Activity;
import org.springframework.batch.item.ItemWriter;
import java.util.List;

public class ActivityPatternItemWriter implements ItemWriter<Activity> {
    @Override
    public void write(List<? extends Activity> list) throws Exception {
        list.forEach(e -> {
            System.out.println("Desde writer - START : " + e.getActivityPattern());
        });
    }
}

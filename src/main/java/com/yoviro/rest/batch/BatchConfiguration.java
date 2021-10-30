package com.yoviro.rest.batch;

import com.yoviro.rest.batch.activity.*;
import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.ActivityPattern;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Value("${batch.activities.code}")
    public String ACTIVITY_BATCH_CODE;

    @Value("${batch.activities.step.code}")
    public String ACTIVITY_CREATE_ACTIVITIES_STEP_CODE;

    @Bean(name = "batchAndSteps")
    public Map<String, String> batchsAndSteps() {
        return Map.ofEntries(Map.entry(this.ACTIVITY_BATCH_CODE, this.ACTIVITY_CREATE_ACTIVITIES_STEP_CODE));
    }

    @Bean
    @StepScope
    public ActivityPatternItemReader reader() {
        return new ActivityPatternItemReader();
    }

    @Bean
    public ActivityPatternItemProcessor processor() {
        return new ActivityPatternItemProcessor();
    }

    @Bean
    public ItemWriter writer() {
        return new ActivityPatternItemWriter();
    }

    @Bean
    public Job activities() {
        Job job = jobBuilderFactory.get(ACTIVITY_BATCH_CODE)
                .incrementer(new RunIdIncrementer())
                .listener(new ActivityPatternJobExecutionListener())
                .preventRestart()
                .flow(createActivities())
                .end()
                .build();
        return job;
    }

    @Bean
    public Step createActivities() {
        TaskletStep step = stepBuilderFactory.get(ACTIVITY_CREATE_ACTIVITIES_STEP_CODE)
                .<ActivityPattern, List<Activity>>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .listener(new ActivityPatternItemReaderListener())
                .listener(new ActivityPatternItemProcessListener())
                .listener(new ActivityPatternItemWriterListener())
                .build();

        return step;
    }
}
package com.yoviro.rest.batch.activity;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.ActivityPattern;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Function;

@Configuration
public class BatchConfiguration {
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public ActivityPatternItemReader reader() {
        return new ActivityPatternItemReader();
    }

    @Bean
    public ActivityPatternItemProcessor processor() {
        return new ActivityPatternItemProcessor();
    }

    @Bean
    public ActivityPatternItemWriter writer() {
        return new ActivityPatternItemWriter();
    }

    @Bean
    public Job job() {
        Job job = jobBuilderFactory.get("job1")
                //.listener(jobExecutionListener)
                .flow(step())
                .end()
                .build();
        return job;
    }

    @Bean
    public Step step() {

        TaskletStep step = stepBuilderFactory.get("step1")
                .<ActivityPattern, Activity>chunk(100)
                .reader(reader())
                .processor(processor())
                .writer(writer())
/*                .listener(readerListener)
                .listener(creditCardItemProcessListener)
                .listener(writerListener)*/
                .build();

        return step;
    }
}
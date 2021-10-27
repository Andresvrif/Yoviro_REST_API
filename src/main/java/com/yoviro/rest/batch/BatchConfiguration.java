package com.yoviro.rest.batch;

import com.yoviro.rest.batch.activity.*;
import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import com.yoviro.rest.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
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
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

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
    public Job job() {
        Job job = jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .listener(new ActivityPatternJobExecutionListener())
                .preventRestart()
                .flow(step())
                .end()
                .build();
        return job;
    }

    @Bean
    public Step step() {
        TaskletStep step = stepBuilderFactory.get("step1")
                .<ActivityPattern, List<Activity>>chunk(100)
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
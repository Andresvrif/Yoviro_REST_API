package com.yoviro.rest.controller;

import com.yoviro.rest.batch.BatchConfiguration;
import com.yoviro.rest.security.service.IJWTService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/api/batchs")
public class BatchRestController {
    private static String USER_NAME_KEY = "userName";

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    BatchConfiguration batchConfiguration;

    @Autowired
    private IJWTService jwtService;

    @Resource(name = "batchAndSteps")
    private Map<String, String> batchAndSteps;

    @PostMapping("/run")
    public String runBatch(@RequestHeader(name = "Authorization") String authorization,
                           @RequestParam String batchCode) throws Exception {
        //Retrieve userName from token
        String token = jwtService.retrieveToken(authorization);
        String userName = jwtService.getUserName(token);
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("userName", userName)
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            return jobExecution.getStatus().toString();
        } catch (Exception ex) {
            return BatchStatus.FAILED.toString();
        }
    }

    @GetMapping("/statistics")
    public ArrayList batchStatistics() {
        ArrayList response = new ArrayList<>();
        batchAndSteps.forEach((k, v) -> response.add(retrieveStaticsFromJobInstance(k, v)));
        return response;
    }

    private Map<String, Object> retrieveStaticsFromJobInstance(String batchCode,
                                                               String stepCode) {
        JobInstance jobInstance = jobExplorer.getLastJobInstance(batchCode);
        JobExecution jobExecution = jobExplorer.getLastJobExecution(jobInstance);
        StepExecution stepExecution = jobRepository.getLastStepExecution(jobInstance, stepCode);

        return Map.ofEntries(
                Map.entry("batchCode", batchCode),
                Map.entry("stepCode", stepExecution.getStepName()),
                Map.entry("author", jobExecution.getJobParameters().getString(USER_NAME_KEY)),
                Map.entry("readCount", stepExecution.getReadCount()),
                Map.entry("writeCount", stepExecution.getWriteCount()),
                Map.entry("status", stepExecution.getExitStatus().getExitCode()),
                Map.entry("startDate", LocalDateTime.ofInstant(stepExecution.getStartTime().toInstant(), ZoneId.systemDefault())),
                Map.entry("endDate", LocalDateTime.ofInstant(stepExecution.getEndTime().toInstant(), ZoneId.systemDefault()))
        );
    }
}
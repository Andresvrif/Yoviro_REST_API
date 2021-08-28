package com.yoviro.rest.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/batchs")
public class BatchRestController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @PostMapping("/run")
    public String cancelAgreement(@RequestParam String batchName) throws Exception {
        System.out.println("Batch ejecutado : " + batchName);

        Map<String, JobParameter> parameters = new HashMap<>();
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        return jobExecution.getStatus().toString();
    }
}

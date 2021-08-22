package com.yoviro.rest.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
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
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters(parameters));
        return "OK";
    }
}

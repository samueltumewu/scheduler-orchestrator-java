package com.samtumewu.schedulerorchestrator.controller;

import com.samtumewu.schedulerorchestrator.SchedulerOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class MainController {

    @Autowired
    private SchedulerOrchestrator schedulerOrchestrator;

    @PostMapping("/refresh/{jobName}")
    public void refreshJobByName(@PathVariable("jobName") String jobName){
        schedulerOrchestrator.refreshByName(jobName);
    }
}

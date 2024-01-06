package com.samtumewu.schedulerorchestrator;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
public class SchedulerOrchestrator {
    @Autowired
    FirstJob firstJob;
    @Autowired
    SecondJob secondJob;

    private static final Map<String, IScheduler> schedJobsMap = new HashMap<>();
    @PostConstruct
    public void initScheduler() {
        schedJobsMap.put("firstJob", firstJob);
        schedJobsMap.put("secondJob", secondJob);
        System.out.println(">>>Init part");
    }

    public void refreshByName(String jobName) {
        schedJobsMap.get(jobName).refresh();
    }

}
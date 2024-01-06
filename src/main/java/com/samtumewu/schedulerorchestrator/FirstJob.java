package com.samtumewu.schedulerorchestrator;

import io.micrometer.common.util.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
@EnableScheduling
public class FirstJob implements SchedulingConfigurer, IScheduler {

    private static String JOB_NAME = "FirstJob";
    private static int CONST_COUNTER=2;
    private ScheduledTask scheduledTask;
    private ScheduledTaskRegistrar scheduledTaskRegistrar;

    private int counter =0;
    private int sumCounter(){
        return counter+=CONST_COUNTER;
    }
    @Override
    public void start() {
        System.out.println(">>>start "+JOB_NAME);
        Runnable slikSendRequestJob = () -> {
            System.out.printf("%20s -- %s -- %d -- %s\n", JOB_NAME, LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), sumCounter(), Thread.currentThread().getName());
        };
        Trigger triggerSlikSendRequest = triggerContext -> {
            String cron = null;
            cron = "0/5 * * * * ?";
            CronTrigger cronTrigger = new CronTrigger(cron);
            Instant nextExecutionTime = cronTrigger.nextExecution(triggerContext);
            return Date.from(nextExecutionTime).toInstant();
//            return cronTrigger.nextExecutionTime(triggerContext).toInstant();
        };
        TriggerTask triggerTask = new TriggerTask(slikSendRequestJob, triggerSlikSendRequest);
        this.scheduledTask = this.scheduledTaskRegistrar.scheduleTriggerTask(triggerTask);
    }

    @Override
    public boolean stop() {
        try {
            System.out.println(">>>destroying " + JOB_NAME + " " + scheduledTask.toString() +" ~ "+ Thread.currentThread().getName());
            this.scheduledTask.cancel();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void refresh() {
        if (stop()) {
            System.out.println(">>>restarting " + JOB_NAME + " " + scheduledTask.toString() +" ~ "+ Thread.currentThread().getName());
            start();
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.scheduledTaskRegistrar = taskRegistrar;
        start();
    }
}

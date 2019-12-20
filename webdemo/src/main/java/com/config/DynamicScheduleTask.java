package com.config;

import com.dao.master.CronDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalTime;

/**
 * 动态定时任务
 * @author AlbertXe
 * @date 2019-12-20 14:44
 */
@EnableScheduling
@Configuration
public class DynamicScheduleTask implements SchedulingConfigurer {
    @Autowired
    CronDao cronDao;


    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(()-> System.out.println("执行动态定时任务"+ LocalTime.now().toString()), (context)->{
            String cron = cronDao.getCron();
           return new CronTrigger(cron).nextExecutionTime(context);
        });
    }
}

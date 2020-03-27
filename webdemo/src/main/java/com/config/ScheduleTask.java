package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author AlbertXe
 * @date 2019-12-19 19:31
 */
@Configuration
@EnableScheduling
public class ScheduleTask {

    @Scheduled(cron = "0/55 * * * * ?")
//    @Scheduled(fixedRate = 5000)
    public void task1() {
        System.out.println("执行静态定时任务1");
    }
}

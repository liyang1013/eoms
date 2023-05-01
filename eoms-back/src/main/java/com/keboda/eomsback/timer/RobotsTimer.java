package com.keboda.eomsback.timer;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class RobotsTimer {

    @Scheduled(cron = "0 0/1 * * * ?")
    private void status(){
        System.out.println("定时器执行");
    }
}

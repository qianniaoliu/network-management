package com.network.management.agent;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 数据采集定时任务
 * @author yyc
 * @date 2020/9/12 21:12
 */
@Component
public class AgentTaskScheduler {
    @Scheduled(cron = "${collect.interval.time}")
    public void collectTimer(){

    }
}

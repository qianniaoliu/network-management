package com.network.management.agent;

import com.network.management.Equipment;
import com.network.management.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 数据采集定时任务
 * @author yyc
 * @date 2020/9/12 21:12
 */
@Component
public class AgentTaskScheduler {
    @Autowired
    private EquipmentService equipmentService;
    @Scheduled(cron = "${collect.interval.time}")
    public void collectTimer(){
        List<Equipment> equipments = equipmentService.getAllEquipments();
    }
}

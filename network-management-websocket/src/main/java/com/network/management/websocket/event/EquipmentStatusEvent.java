/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.network.management.websocket.event;

import com.network.management.websocket.EquipmentStatusCombination;

import java.util.EventObject;
import java.util.concurrent.ConcurrentMap;

/**
 * 设备状态事件
 *
 * @author shenlong
 */
public class EquipmentStatusEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public EquipmentStatusEvent(ConcurrentMap<Integer, EquipmentStatusCombination> source) {
        super(source);
    }
}
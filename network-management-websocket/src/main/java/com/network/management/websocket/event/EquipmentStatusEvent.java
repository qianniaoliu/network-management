/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.network.management.websocket.event;

import java.util.EventObject;

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
    public EquipmentStatusEvent(Object source) {
        super(source);
    }
}
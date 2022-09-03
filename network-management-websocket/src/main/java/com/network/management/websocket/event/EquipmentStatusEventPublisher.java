/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.network.management.websocket.event;

import com.alibaba.fastjson.JSONObject;
import com.network.management.websocket.EquipmentStatusCombination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 设备状态事件发布器
 *
 * @author shenlong
 */
@Component
@Slf4j
public class EquipmentStatusEventPublisher {

    /**
     * 缓存所有客户端连接
     */
    private List<WebSocketSession> webSocketSessions = new ArrayList<>();

    /**
     * 缓存上次事件数据，用于新连接用户使用
     */
    private ConcurrentMap<Integer, EquipmentStatusCombination> cacheData = new ConcurrentHashMap<>();

    /**
     * 增加客户端连接会话
     *
     * @param session
     */
    public void addSession(WebSocketSession session) {
        webSocketSessions.add(session);
        // 新连接客户需立即获取到最新数据
        doSendMessage(session, this.cacheData.values());
    }

    /**
     * 删除客户端连接会话
     *
     * @param session
     */
    public void removeSession(WebSocketSession session) {
        webSocketSessions.remove(session);
    }

    /**
     * 发布事件
     *
     * @param event
     */
    public void publish(EquipmentStatusEvent event) {
        ConcurrentMap<Integer, EquipmentStatusCombination> source = (ConcurrentMap<Integer, EquipmentStatusCombination>) event.getSource();
        this.cacheData = source;
        webSocketSessions.forEach(session -> {
            doSendMessage(session, source.values());
        });
    }

    /**
     * 真正发送消息
     *
     * @param session
     * @param data
     */
    public void doSendMessage(WebSocketSession session, Object data) {
        if (Objects.isNull(data)) {
            return;
        }
        try {
            TextMessage message = new TextMessage(JSONObject.toJSONString(data));
            session.sendMessage(message);
        } catch (IOException e) {
            log.error("发送给客户端消息失败,message:{}", JSONObject.toJSONString(data));
        }
    }
}
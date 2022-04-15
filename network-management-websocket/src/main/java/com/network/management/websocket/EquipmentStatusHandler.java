/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.network.management.websocket;

import com.network.management.websocket.event.EquipmentStatusEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 基站状态实时同步处理器
 *
 * @author shenlong
 */
@AllArgsConstructor
public class EquipmentStatusHandler implements WebSocketHandler {


    private final EquipmentStatusEventPublisher publisher;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        publisher.addSession(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        publisher.removeSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
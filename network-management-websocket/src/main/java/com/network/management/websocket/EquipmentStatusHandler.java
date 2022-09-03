/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.network.management.websocket;

import com.network.management.websocket.event.EquipmentStatusEventPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EquipmentStatusHandler implements WebSocketHandler {

    private final EquipmentStatusEventPublisher publisher;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("新连接加入，sessionId:{}", session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        publisher.addSession(String.valueOf(message.getPayload()), session);
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
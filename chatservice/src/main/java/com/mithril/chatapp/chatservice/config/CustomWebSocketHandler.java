package com.mithril.chatapp.chatservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mithril.chatapp.chatservice.dto.WebSocketMessage;
import com.mithril.chatapp.chatservice.service.interfaces.MessageOrchestratorService;
import com.mithril.chatapp.chatservice.util.WebSocketSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import com.mithril.chatapp.chatservice.redis.RedisChannelManager;

@Slf4j
@Component
public class CustomWebSocketHandler implements WebSocketHandler {

    @Autowired
    private RedisChannelManager redisChannelManager;

    @Autowired
    private MessageOrchestratorService messageOrchestratorService;

    private final WebSocketSessionManager webSocketSessionManager;

    // To map the JSON object to a Java object
    private final ObjectMapper objectMapper = new ObjectMapper();

    CustomWebSocketHandler() {
        this.webSocketSessionManager = new WebSocketSessionManager();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        // adding websocket session to session manager to manage the session
        this.webSocketSessionManager.addWebSocketSession(session);
        log.info("New connection established: {}", session.getId());

        // subscribing to the redis channel
        redisChannelManager.subscribeUserChannel(WebSocketSessionUtil.getUserId(session)
                .orElseThrow(() -> new RuntimeException("User ID not found in WebSocket session")));
        log.info("Subscribed to redis channel for user: {}", session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, org.springframework.web.socket.WebSocketMessage<?> webSocketMessage) throws Exception {
        String payload = webSocketMessage.getPayload().toString();
        WebSocketMessage customWebSocketMessage = objectMapper.readValue(payload, WebSocketMessage.class);

        // Extracting the receiver's uid from the message
        String receiverUid = customWebSocketMessage.getReceiver();
        log.info("Received message: {} from: {} to: {}", customWebSocketMessage.getContent(), customWebSocketMessage.getSender(), receiverUid);

        // Publishing the message to the redis channel
        messageOrchestratorService.processMessage(receiverUid, customWebSocketMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("Error occurred at session: {}", session.getId(), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // removing websocket session from session manager
        webSocketSessionManager.removeWebSocketSession(session);
        log.info("Connection closed: {}", session.getId());

        // unsubscribing from the redis channel
        redisChannelManager.unsubscribeUserChannel(WebSocketSessionUtil.getUserId(session)
                .orElseThrow(() -> new RuntimeException("User ID not found in WebSocket session")));
        log.info("Unsubscribed from redis channel for user: {}", session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

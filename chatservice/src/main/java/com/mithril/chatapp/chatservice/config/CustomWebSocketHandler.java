package com.mithril.chatapp.chatservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mithril.chatapp.chatservice.dto.WebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Slf4j
@Component
public class CustomWebSocketHandler implements WebSocketHandler {

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
    }

    @Override
    public void handleMessage(WebSocketSession session, org.springframework.web.socket.WebSocketMessage<?> webSocketMessage) throws Exception {
        String payload = webSocketMessage.getPayload().toString();
        WebSocketMessage customWebSocketMessage = objectMapper.readValue(payload, WebSocketMessage.class);

        // Extracting the receiver's uid from the message
        String receiverUid = customWebSocketMessage.getReceiver();
        log.info("Received message: {} from: {} to: {}", customWebSocketMessage.getContent(), customWebSocketMessage.getSender(), receiverUid);

        // Additional logic to handle the message
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
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

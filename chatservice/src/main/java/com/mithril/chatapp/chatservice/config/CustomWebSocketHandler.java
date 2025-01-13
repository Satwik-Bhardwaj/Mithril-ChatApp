package com.mithril.chatapp.chatservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mithril.chatapp.chatservice.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Component
public class CustomWebSocketHandler implements WebSocketHandler {

    // To map the JSON object to a Java object
    private final ObjectMapper objectMapper = new ObjectMapper();

    // To store active sessions
    private final ConcurrentHashMap<WebSocketSession, Long> activeSessions = new ConcurrentHashMap<>();
    // To schedule a task to check for inactive sessions
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public CustomWebSocketHandler() {
        // Schedule a task to check for inactive sessions every 10 seconds
        scheduler.scheduleAtFixedRate(() -> {
            activeSessions.forEach((session, lastPing) -> {
                if (System.currentTimeMillis() - lastPing > 15000) {   // Close the session if inactive for more than 10 seconds
                    try {
                        session.close(CloseStatus.GOING_AWAY);
                        activeSessions.remove(session);
                    } catch (Exception e) {
                        log.error("Error occurred while closing session: {}", session.getId(), e);
                    }
                } else {    // Send ping to the session if active
                    try {
                        Message ping = new Message("PING", null, System.currentTimeMillis(), "server", session.getId());
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(ping)));
                    } catch (Exception e) {
                        log.error("Error occurred while sending ping to session: {}", session.getId(), e);
                    }
                }
            });
        }, 0, 10000, java.util.concurrent.TimeUnit.MILLISECONDS);
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uid = extractUidFromUri(Objects.requireNonNull(session.getUri()).toString());
        activeSessions.put(session, System.currentTimeMillis());
        log.info("New connection established: {} with uid: {}", session.getId(), uid);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();

        Message customMessage = objectMapper.readValue(payload, Message.class);

        if ("PONG".equals(customMessage.getType())) {
            activeSessions.put(session, System.currentTimeMillis());
        } else {
            log.info("Received message: {} from session: {}", customMessage, session.getId());
            // Broadcast the message to all active sessions
            activeSessions.forEach((activeSession, lastPing) -> {
                try {
                    activeSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(customMessage)));
                } catch (Exception e) {
                    log.error("Error occurred while sending message to session: {}", activeSession.getId(), e);
                }
            });
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("Error occurred at session: {}", session.getId(), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        activeSessions.remove(session);
        log.info("Connection closed: {}", session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private String extractUidFromUri(String uri) {
        // Assuming URI format: /chat-ws/{uid}
        String[] parts = uri.split("/");
        return parts[parts.length - 1]; // Extract the last part as 'uid'
    }
}

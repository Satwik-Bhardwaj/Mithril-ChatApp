package com.mithril.chatapp.chatservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketSessionManager {

    // Map to store active sessions using user id as key
    private final Map<String, WebSocketSession> webSocketSessionByUserId = new ConcurrentHashMap<>();

    public void addWebSocketSession(WebSocketSession webSocketSession) {
        String sessionId = webSocketSession.getId();
        log.info("adding session: {} to add session manager", sessionId);
        this.webSocketSessionByUserId.put(sessionId, webSocketSession);
        log.info("added session: {} to websocket session manager", sessionId);
    }

    public void removeWebSocketSession(WebSocketSession webSocketSession) {
        String sessionId = webSocketSession.getId();
        log.info("removing session: {} from session manager", sessionId);
        this.webSocketSessionByUserId.remove(sessionId);
        log.info("removed session: {} from websocker session manager", sessionId);
    }

    public Optional<WebSocketSession> getWebSocketSession(String userId) {
        return Optional.ofNullable(this.webSocketSessionByUserId.get(userId));
    }
}

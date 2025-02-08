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
        String userId = WebSocketHelper. getUserIdFromSessionAttribute(webSocketSession);
        log.info("got request to add session id {} for user id {} ", webSocketSession.getId(), userId);
        this.webSocketSessionByUserId.put(userId, webSocketSession);
        log.info("added session id {} for user id {}", webSocketSession.getId(), userId);
    }

    public void removeWebSocketSession(WebSocketSession webSocketSession) {
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(webSocketSession);
        log.info("got request to remove session id {} for user id {}", webSocketSession.getId(), userId);
        this.webSocketSessionByUserId.remove(userId);
        log.info("removed session id {} for user id {}", webSocketSession.getId(), userId);
    }

    public Optional<WebSocketSession> getWebSocketSession(String userId) {
        return Optional.ofNullable(this.webSocketSessionByUserId.get(userId));
    }
}

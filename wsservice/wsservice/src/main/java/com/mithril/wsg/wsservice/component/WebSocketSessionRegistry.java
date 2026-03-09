package com.mithril.wsg.wsservice.component;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionRegistry {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    public void removeSession(String sessionId) throws IOException {
        WebSocketSession session = sessions.remove(sessionId);

        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    public WebSocketSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
}

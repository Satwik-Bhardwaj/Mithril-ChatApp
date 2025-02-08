package com.mithril.chatapp.chatservice.config;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketHelper {
    public static String userIdKey = "userId";

    public static String getUserIdFromSessionAttribute(WebSocketSession webSocketSession) {
        return (String) webSocketSession.getAttributes().get(userIdKey);
    }

    public static String getUserIdFromPath(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}

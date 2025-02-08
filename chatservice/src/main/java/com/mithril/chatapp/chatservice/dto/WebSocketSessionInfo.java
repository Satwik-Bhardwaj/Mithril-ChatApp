package com.mithril.chatapp.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
@AllArgsConstructor
public class WebSocketSessionInfo {

    private WebSocketSession session;

    private long lastPing;
}

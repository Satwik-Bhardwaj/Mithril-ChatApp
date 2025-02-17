package com.mithril.chatapp.chatservice.service.interfaces;

import com.mithril.chatapp.chatservice.dto.WebSocketMessage;
import org.springframework.data.redis.connection.Message;

public interface MessageOrchestratorService {

    void processMessage(String receiverId, WebSocketMessage message);

    void onMessageReceived(Message message);

}

package com.mithril.chatapp.chatservice.redis;

import com.mithril.chatapp.chatservice.config.WebSocketSessionManager;
import com.mithril.chatapp.chatservice.service.interfaces.MessageOrchestratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    @Lazy
    MessageOrchestratorService messageOrchestratorService;

    private final WebSocketSessionManager webSocketSessionManager;

    public RedisMessageSubscriber(WebSocketSessionManager webSocketSessionManager) {
        this.webSocketSessionManager = webSocketSessionManager;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        messageOrchestratorService.onMessageReceived(message);
    }
}

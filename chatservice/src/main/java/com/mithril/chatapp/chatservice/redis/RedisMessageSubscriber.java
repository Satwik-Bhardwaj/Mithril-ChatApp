package com.mithril.chatapp.chatservice.redis;

import com.mithril.chatapp.chatservice.config.WebSocketSessionManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
@Service
public class RedisMessageSubscriber implements MessageListener {

    private final WebSocketSessionManager webSocketSessionManager;

    public RedisMessageSubscriber(WebSocketSessionManager webSocketSessionManager) {
        this.webSocketSessionManager = webSocketSessionManager;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        log.info("Received message: {} from channel: {}", body, channel);

        // sending the message to the websocket session
        WebSocketSession webSocketSession = webSocketSessionManager.getWebSocketSession(channel).orElseThrow(() -> new RuntimeException("No session found for user id " + channel));

        try {
            webSocketSession.sendMessage(new TextMessage(body));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

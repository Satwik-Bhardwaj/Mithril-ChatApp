package com.mithril.chatapp.chatservice.redis;

import com.mithril.chatapp.chatservice.config.WebSocketSessionManager;
import io.lettuce.core.pubsub.RedisPubSubListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class SubscriberHelper implements RedisPubSubListener<String, String> {

    private final WebSocketSessionManager webSocketSessionManager;

    public SubscriberHelper(WebSocketSessionManager webSocketSessionManager) {
        this.webSocketSessionManager = webSocketSessionManager;
    }

    @Override
    public void message(String channel, String message) {
        log.info("got the message on redis {} and {}", channel, message);
        WebSocketSession ws = this.webSocketSessionManager.getWebSocketSession(channel).orElseThrow(() -> new RuntimeException("No session found for user id " + channel));
        try {
            ws.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void message(String s, String k1, String s2) {

    }

    @Override
    public void subscribed(String s, long l) {

    }

    @Override
    public void psubscribed(String s, long l) {

    }

    @Override
    public void unsubscribed(String s, long l) {

    }

    @Override
    public void punsubscribed(String s, long l) {

    }

}
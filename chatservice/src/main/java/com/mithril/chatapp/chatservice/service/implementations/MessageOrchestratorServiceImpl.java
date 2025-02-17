package com.mithril.chatapp.chatservice.service.implementations;

import com.mithril.chatapp.chatservice.config.WebSocketSessionManager;
import com.mithril.chatapp.chatservice.dto.WebSocketMessage;
import com.mithril.chatapp.chatservice.redis.RedisChannelManager;
import com.mithril.chatapp.chatservice.redis.RedisMessagePublisher;
import com.mithril.chatapp.chatservice.service.interfaces.MessageOrchestratorService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static com.mithril.chatapp.chatservice.util.ChatConstants.WS_MSG_CHANNEL;

@Slf4j
@Service
public class MessageOrchestratorServiceImpl implements MessageOrchestratorService {

    @Lazy
    @Autowired
    RedisChannelManager redisChannelManager;

    @Autowired
    RedisMessagePublisher redisMessagePublisher;

    @Autowired
    WebSocketSessionManager webSocketSessionManager;

    @Override
    public void processMessage(String receiverId, WebSocketMessage message) {

        if (receiverId == null || receiverId.isEmpty()) {
            throw new IllegalArgumentException("User id cannot be null or empty");
        }

        String channelName = WS_MSG_CHANNEL + receiverId;
        if (redisChannelManager.isSubscribedUserChannel(receiverId)) {
            // Publish the message to the user channel
            redisMessagePublisher.publish(channelName, message);
        }
    }

    @Override
    public void onMessageReceived(Message message) {
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

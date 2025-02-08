package com.mithril.chatapp.chatservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mithril.chatapp.chatservice.dto.Message;
import com.mithril.chatapp.chatservice.dto.WebSocketSessionInfo;
import com.mithril.chatapp.chatservice.redis.Publisher;
import com.mithril.chatapp.chatservice.redis.Subscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Component
public class CustomWebSocketHandler implements WebSocketHandler {

    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @Autowired
    private Publisher redisPublisher;

    @Autowired
    private Subscriber redisSubscriber;

    // To map the JSON object to a Java object
    private final ObjectMapper objectMapper = new ObjectMapper();

    // To schedule a task to check for inactive sessions
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public CustomWebSocketHandler(WebSocketSessionManager webSocketSessionManager, Publisher redisPublisher, Subscriber redisSubscriber) {
        // Initialize the WebSocketSessionManager, Publisher, and Subscriber
        this.webSocketSessionManager = webSocketSessionManager;
        this.redisPublisher = redisPublisher;
        this.redisSubscriber = redisSubscriber;


        // Schedule a task to check for inactive sessions every 10 seconds
//        scheduler.scheduleAtFixedRate(() -> {
//                try {
//                    activeSessions.forEach((sessionId, sessionInfo) -> {
//                        if (System.currentTimeMillis() - sessionInfo.getLastPing() > 15000) {   // Close the session if inactive for more than 15 seconds
//                            activeSessions.remove(sessionId);
//                            log.info("Session {} is inactive for more than 15 seconds. Closing the session.", sessionId);
//                        }
//                    });
//                } catch (Exception e) {
//                    log.error("Error occurred during session cleanup task", e);
//                }
//        }, 0, 10000, java.util.concurrent.TimeUnit.MILLISECONDS);
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.webSocketSessionManager.addWebSocketSession(session);
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(session);
        this.redisSubscriber.subscribe(userId);

//        String uid = extractUidFromUri(Objects.requireNonNull(session.getUri()).toString());
//        activeSessions.put(session.getId(), new WebSocketSessionInfo(session, System.currentTimeMillis()));
        log.info("New connection established: {} with userId: {}", session.getId(), userId);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();
        Message customMessage = objectMapper.readValue(payload, Message.class);

        if ("PING".equals(customMessage.getType())) {
            // sending pong
            session.sendMessage(new TextMessage("{\"type\":\"PONG\"}"));
        } else {
            log.info("Received message: {} from session: {}", customMessage, session.getId());
            // TODO : Handle non-ping message
            String receiverId = customMessage.getReceiver();
            String userId = WebSocketHelper.getUserIdFromSessionAttribute(session);
            log.info("got the payload {} and going to send to channel {}", payload, receiverId);
            this.redisPublisher.publish(receiverId, userId + ":" + customMessage.getContent());

        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("Error occurred at session: {}", session.getId(), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        webSocketSessionManager.removeWebSocketSession(session);
        log.info("Connection closed: {} with uid: {}", session.getId(), extractUidFromUri(session.getUri().toString()));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private String extractUidFromUri(String uri) {
        // Assuming URI format: /chat-ws/{uid}
        String[] parts = uri.split("/");
        return parts[parts.length - 1]; // Extract the last part as 'uid'
    }
}

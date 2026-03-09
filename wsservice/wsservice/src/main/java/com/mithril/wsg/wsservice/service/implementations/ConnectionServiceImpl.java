package com.mithril.wsg.wsservice.service.implementations;

import com.mithril.wsg.wsservice.component.WebSocketSessionRegistry;
import com.mithril.wsg.wsservice.dto.ChatMessage;
import com.mithril.wsg.wsservice.exception.BadRequestException;
import com.mithril.wsg.wsservice.service.interfaces.ConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Slf4j
@Service
public class ConnectionServiceImpl implements ConnectionService {

    private final WebSocketSessionRegistry webSocketSessionRegistry;

    public ConnectionServiceImpl(WebSocketSessionRegistry webSocketSessionRegistry) {
        this.webSocketSessionRegistry = webSocketSessionRegistry;
    }

    @Override
    public void sendMessageToSession(String sessionId, ChatMessage message) {

        WebSocketSession receiverSession = webSocketSessionRegistry.getSession(sessionId);

        WebSocketMessage<ChatMessage> webSocketMessage = new WebSocketMessage<>() {
            @Override
            public ChatMessage getPayload() {
                return message;
            }

            @Override
            public int getPayloadLength() {
                return 0;
            }

            @Override
            public boolean isLast() {
                return true;
            }
        };

        try {
            receiverSession.sendMessage(webSocketMessage);
        } catch (IOException e) {
            log.error("Failed to send message to sessionId: {}. Error: {}", sessionId, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessageToChatService(String sessionId, Object message) {

        log.info("Received message from sessionId: {}. Forwarding to chat service for processing.", sessionId);

        ChatMessage chatMessage;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            chatMessage = objectMapper.readValue(message.toString(), ChatMessage.class);

        } catch (Exception e) {
            log.error("Failed to parse message from sessionId: {}. Error: {}", sessionId, e.getMessage());
            throw new BadRequestException("Invalid message format", e);
        }

        // TODO: remove this log
        log.info("Received message from sessionId: {}. Message content: {}", sessionId, chatMessage.toString());

        // TODO: send the message


    }

}

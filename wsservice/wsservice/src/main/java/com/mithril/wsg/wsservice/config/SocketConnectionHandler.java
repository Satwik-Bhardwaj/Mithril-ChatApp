package com.mithril.wsg.wsservice.config;

import com.mithril.wsg.wsservice.component.WebSocketSessionRegistry;
import com.mithril.wsg.wsservice.exception.BadRequestException;
import com.mithril.wsg.wsservice.service.interfaces.ConnectionService;
import com.mithril.wsg.wsservice.service.interfaces.GatewayRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
    public class SocketConnectionHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final WebSocketSessionRegistry webSocketSessionRegistry;

    private final GatewayRegistry gatewayRegistry;

    private final ConnectionService connectionService;

    public SocketConnectionHandler(WebSocketSessionRegistry webSocketSessionRegistry, GatewayRegistry gatewayRegistry, ConnectionService connectionService) {
        this.webSocketSessionRegistry = webSocketSessionRegistry;
        this.gatewayRegistry = gatewayRegistry;
        this.connectionService = connectionService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("New websocket connection establishing: {}", session.getId());
        webSocketSessionRegistry.addSession(session);
        gatewayRegistry.addGatewaySessionMapping(session.getId());
        log.info("Websocket connection established: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Websocket connection closing: {}", session.getId());
        try {
            webSocketSessionRegistry.removeSession(session.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gatewayRegistry.removeGatewaySessionMapping(session.getId());
        log.info("Websocket connection closed: {}", session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        log.info("handling websocket message: {} from session: {}", message.getPayload(), session.getId());

        String id = session.getId();
        Object payload = message.getPayload();

        try {
            connectionService.sendMessageToChatService(id, payload);
        } catch (BadRequestException e) {
            log.warn("Failed to send message to chat service for sessionId: {}. Error: {}", id, e.getMessage());
            sendError(session, "INVALID_MESSAGE_FORMAT");
        }
        log.info("handled websocket message: {} from session: {}", message.getPayload(), session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("Error occurred at session: {}", session.getId(), exception);
    }

    private void sendError(WebSocketSession session, String errorCode) {
        try {

            String errorJson =
                    objectMapper.writeValueAsString(Map.of(
                            "type", "ERROR",
                            "code", errorCode
                    ));

            session.sendMessage(new TextMessage(errorJson));

        } catch (Exception e) {
            log.error("Failed to send error message to session: {}. Error: {}", session.getId(), e.getMessage());
            throw new RuntimeException("Failed to send error message to session: " + session.getId(), e);
        }
    }
}

package com.mithril.wsg.wsservice.service.interfaces;

import com.mithril.wsg.wsservice.dto.ChatMessage;

public interface ConnectionService {

    /**
        * Sends a message to a specific session.
     * @param sessionId the session ID to send the message to
     * @param message the message to send
     */
    void sendMessageToSession(String sessionId, ChatMessage message);

    /**
     * Sends a message to the chat service for processing.
     * The chat service will then determine which session(s) to send the response to.
     * @param sessionId session that
     * @param message
     */
    void sendMessageToChatService(String sessionId, Object message);
}

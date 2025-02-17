package com.mithril.chatapp.chatservice.util;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

public class WebSocketSessionUtil {

    /**
     * Extract userId from WebSocketSession URI
     *
     * @param session WebSocketSession
     * @return userId
     */
    public static Optional<String> getUserId(WebSocketSession session) {
        URI uri = session.getUri();
        if (uri == null) {
            return Optional.empty();
        }

        // Extract userId from query parameter
        return Optional.ofNullable(UriComponentsBuilder.fromUri(uri).build().getQueryParams().getFirst("userId"));
    }
}

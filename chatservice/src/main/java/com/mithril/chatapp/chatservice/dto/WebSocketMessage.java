package com.mithril.chatapp.chatservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized @Builder
public class WebSocketMessage {

    private String type;
    private String content;
    private Long timestamp;
    private String sender;
    private String receiver;

    @JsonCreator
    public WebSocketMessage(
            @JsonProperty("type") String type,
            @JsonProperty("content") String content,
            @JsonProperty("timestamp") Long timestamp,
            @JsonProperty("sender") String sender,
            @JsonProperty("receiver") String receiver
    ) {
        this.type = type;
        this.content = content;
        this.timestamp = timestamp;
        this.sender = sender;
        this.receiver = receiver;
    }
}


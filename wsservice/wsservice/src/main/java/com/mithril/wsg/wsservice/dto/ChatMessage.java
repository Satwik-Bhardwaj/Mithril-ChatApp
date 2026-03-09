package com.mithril.wsg.wsservice.dto;

import lombok.Data;

@Data
public class ChatMessage {

    String receiverId;

    String message;
}

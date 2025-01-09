package com.mithril.chatapp.chatservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message", schema = "chat")
public class Message extends BaseEntity {

    private String content;

    @Column(name = "send_at")
    private LocalDateTime sendAt;

    @Column(name = "sender_id")
    private UUID senderId;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}

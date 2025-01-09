package com.mithril.chatapp.chatservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "user_chat",
        schema = "chat",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "chat_id"}
                )
        }
)
public class UserChat extends BaseEntity {

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

}

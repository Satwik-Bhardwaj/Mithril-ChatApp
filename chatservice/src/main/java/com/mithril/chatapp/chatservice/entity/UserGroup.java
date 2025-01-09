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
        name = "user_group",
        schema = "chat",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "group_id"})
        }
)
public class UserGroup extends BaseEntity {

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}

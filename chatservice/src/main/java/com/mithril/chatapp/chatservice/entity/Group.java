package com.mithril.chatapp.chatservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group", schema = "chat",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id", "admin_id"})
        }
)
public class Group extends BaseEntity {
    private String name;
    private String description;

    @Column(name = "admin_id")
    private UUID adminId;

    @OneToMany(mappedBy = "group")
    private List<Message> messages;
}

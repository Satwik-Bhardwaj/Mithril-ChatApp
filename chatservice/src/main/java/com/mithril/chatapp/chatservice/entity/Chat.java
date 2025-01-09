package com.mithril.chatapp.chatservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat", schema = "chat")
public class Chat extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

}

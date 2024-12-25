package com.mithril.chatapp.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mithril.chatapp.userservice.dto.Contact;
import com.mithril.chatapp.userservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    private String username;
    private String bio;
    private Contact contact;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status status;
}

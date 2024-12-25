package com.mithril.chatapp.userservice.dto.request;

import com.mithril.chatapp.userservice.dto.Contact;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String bio;
    @NotNull
    @Valid
    private Contact contact;
}

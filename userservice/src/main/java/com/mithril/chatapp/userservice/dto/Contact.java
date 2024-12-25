package com.mithril.chatapp.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @NotNull
    private Long phoneNumber;
    @NotBlank (message = "Country Code is mandatory")
    private String countryCode;
}

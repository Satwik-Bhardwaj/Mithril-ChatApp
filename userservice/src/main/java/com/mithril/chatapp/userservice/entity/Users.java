package com.mithril.chatapp.userservice.entity;
import com.mithril.chatapp.userservice.enums.CountryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Schema(name = "Users", description = "Represents a User")
@Entity
@Getter
@Setter
public class Users extends BaseEntity {

    private String username;

    @Schema(description = "Users' Profile Bio")
    private String bio;

    @Schema(description = "Country Code of the Phone number", example = "'US', 'IN' or 'UK', etc.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CountryCode countryCode;

    @Column(unique = true)
    private Long phoneNumber;
}

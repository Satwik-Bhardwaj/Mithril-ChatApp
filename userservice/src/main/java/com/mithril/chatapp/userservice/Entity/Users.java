package com.mithril.chatapp.userservice.entity;
import com.mithril.chatapp.userservice.enums.CountryCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Users extends BaseEntity {

    private String username;

    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CountryCode countryCode;

    @Column(unique = true)
    private Long phoneNumber;
}

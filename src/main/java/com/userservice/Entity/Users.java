package com.userservice.Entity;

import com.userservice.enums.CountryCode;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseEntity{
    private String username;
    private String bio;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CountryCode countryCode;
    @Column(unique = true)
    private Long phoneNumber;

}

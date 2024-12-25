package com.mithril.chatapp.userservice.enums;

import lombok.Getter;

@Getter
public enum CountryCode {
    US("+1"),
    IN("+91"),
    UK("+44");

    private final String code;

    CountryCode(String code) {
        this.code = code;
    }

}

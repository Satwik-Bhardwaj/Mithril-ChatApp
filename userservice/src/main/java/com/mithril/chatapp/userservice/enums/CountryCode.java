package com.mithril.chatapp.userservice.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CountryCode {
    US("+1"),
    IN("+91"),
    UK("+44");

    private final String code;

    CountryCode(String code) {
        this.code = code;
    }

    /**
     * Get the enum value by the string representation of the country code.
     *
     * @param code the country code (e.g., "+1", "+91")
     * @return the corresponding CountryCode enum
     * @throws IllegalArgumentException if the code does not match any CountryCode
     */
    public static CountryCode getByCode(String code) {
        return Arrays.stream(CountryCode.values())
                .filter(countryCode -> countryCode.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid country code: " + code));
    }

    /**
     * Get the enum value by the country name abbreviation.
     *
     * @param name the name abbreviation (e.g., "US", "IN")
     * @return the corresponding CountryCode enum
     * @throws IllegalArgumentException if the name does not match any CountryCode
     */
    public static CountryCode getByName(String name) {
        return Arrays.stream(CountryCode.values())
                .filter(countryCode -> countryCode.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid country name: " + name));
    }

    /**
     * Check if a given code is valid.
     *
     * @param code the country code to validate
     * @return true if the code is valid, false otherwise
     */
    public static boolean isValidCode(String code) {
        return Arrays.stream(CountryCode.values())
                .anyMatch(countryCode -> countryCode.getCode().equals(code));
    }

    /**
     * Check if a given name abbreviation is valid.
     *
     * @param name the name abbreviation to validate
     * @return true if the name is valid, false otherwise
     */
    public static boolean isValidName(String name) {
        return Arrays.stream(CountryCode.values())
                .anyMatch(countryCode -> countryCode.name().equalsIgnoreCase(name));
    }

    /**
     * Get a string representation of all available country codes.
     *
     * @return a comma-separated list of all country codes
     */
    public static String getAllCountryCodes() {
        return Arrays.stream(CountryCode.values())
                .map(CountryCode::getCode)
                .reduce((code1, code2) -> code1 + ", " + code2)
                .orElse("");
    }
}

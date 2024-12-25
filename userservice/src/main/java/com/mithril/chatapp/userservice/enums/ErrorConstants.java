package com.mithril.chatapp.userservice.enums;

import lombok.Getter;

/**
 * Enum containing error codes and messages used across the application.
 */
@Getter
public enum ErrorConstants {

    // General Errors
    INTERNAL_SERVER_ERROR("ERR_INTERNAL_SERVER_ERROR", "An unexpected error occurred. Please try again later."),
    BAD_REQUEST("ERR_BAD_REQUEST", "The request parameters are invalid or incomplete."),
    UNAUTHORIZED("ERR_UNAUTHORIZED", "You are not authorized to access this resource."),
    FORBIDDEN("ERR_FORBIDDEN", "Access to the requested resource is forbidden."),
    RESOURCE_NOT_FOUND("ERR_RESOURCE_NOT_FOUND", "The requested resource could not be found."),
    CONFLICT("ERR_CONFLICT", "A conflict occurred while processing the request."),
    VALIDATION_ERROR("ERR_VALIDATION_ERROR", "Validation failed for the request."),

    // User Service Errors
    USER_NOT_FOUND("ERR_USER_NOT_FOUND", "The specified user does not exist."),
    USER_ALREADY_EXISTS("ERR_USER_ALREADY_EXISTS", "A user with the provided details already exists."),
    INVALID_USER_CREDENTIALS("ERR_INVALID_USER_CREDENTIALS", "The provided user credentials are incorrect."),

    // Chat Service Errors
    CHAT_NOT_FOUND("ERR_CHAT_NOT_FOUND", "The specified chat could not be found."),
    MESSAGE_SEND_FAILURE("ERR_MESSAGE_SEND_FAILURE", "Failed to send the message. Please try again later."),

    // Media Service Errors
    MEDIA_UPLOAD_FAILURE("ERR_MEDIA_UPLOAD_FAILURE", "Failed to upload media. Please try again later."),
    MEDIA_NOT_FOUND("ERR_MEDIA_NOT_FOUND", "The requested media file could not be found."),

    // Notification Service Errors
    NOTIFICATION_FAILURE("ERR_NOTIFICATION_FAILURE", "Failed to send the notification."),

    // Presence Service Errors
    PRESENCE_UPDATE_FAILURE("ERR_PRESENCE_UPDATE_FAILURE", "Failed to update presence status.");

    private final String code;
    private final String message;

    ErrorConstants(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
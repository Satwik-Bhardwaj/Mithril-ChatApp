package com.mithril.chatapp.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel<T> {
    private boolean success;
    private HttpStatus status;
    private T data;
    private String message;
    private LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error_code;

    public static <T> ResponseModel<T> success(T data, HttpStatus status, String message) {
        return new ResponseModel<>(true, status, data, message, LocalDateTime.now(), null);
    }

    public static <T> ResponseModel<T> error(String error_code, HttpStatus status, String message) {
        return new ResponseModel<>(false, status, null, message, LocalDateTime.now(), error_code);
    }
}


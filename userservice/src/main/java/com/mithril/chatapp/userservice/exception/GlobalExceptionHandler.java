package com.mithril.chatapp.userservice.exception;

import com.mithril.chatapp.userservice.enums.ErrorConstants;
import com.mithril.chatapp.userservice.dto.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles UserNotFoundException and returns a structured response.
     *
     * @param ex the UserNotFoundException
     * @return a ResponseEntity with error details and HTTP status 404
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseModel<?>> userNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(
                ResponseModel.error(ErrorConstants.USER_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND, ErrorConstants.USER_NOT_FOUND.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Handles UserAlreadyExistsException and returns a structured response.
     *
     * @param ex the UserAlreadyExistsException
     * @return a ResponseEntity with error details and HTTP status 409
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseModel<?>> userAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(
                ResponseModel.error(ErrorConstants.USER_ALREADY_EXISTS.getCode(), HttpStatus.BAD_REQUEST, ErrorConstants.USER_ALREADY_EXISTS.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    /**
     * Handles InvalidUserCredentialsException and returns a structured response.
     *
     * @param ex the InvalidUserCredentialsException
     * @return a ResponseEntity with error details and HTTP status 401
     */
    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<ResponseModel<?>> invalidUserCredentialsException(InvalidUserCredentialsException ex) {
        return new ResponseEntity<>(
                ResponseModel.error(ErrorConstants.INVALID_USER_CREDENTIALS.getCode(), HttpStatus.BAD_REQUEST, ErrorConstants.INVALID_USER_CREDENTIALS.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Handles generic exceptions and returns a structured response.
     *
     * @param ex the Exception
     * @return a ResponseEntity with error details and HTTP status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModel<?>> genericException(Exception ex) {
        return new ResponseEntity<>(
                ResponseModel.error(ErrorConstants.INTERNAL_SERVER_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage().isBlank() ? ErrorConstants.INTERNAL_SERVER_ERROR.getMessage() : ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
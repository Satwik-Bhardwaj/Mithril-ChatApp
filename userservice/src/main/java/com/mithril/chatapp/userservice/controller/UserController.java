package com.mithril.chatapp.userservice.controller;

import com.mithril.chatapp.userservice.dto.ResponseModel;
import com.mithril.chatapp.userservice.dto.request.UserRequest;
import com.mithril.chatapp.userservice.dto.response.UserResponse;
import com.mithril.chatapp.userservice.service.UserService;
import com.mithril.chatapp.userservice.enums.CountryCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "User Controller", description = "API for managing users")
@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create user", description = "Create a new user using this POST endpoint")
    @PostMapping("/create")
    public ResponseEntity<ResponseModel<UserResponse>> createUser(@Valid @RequestBody UserRequest user){
        log.info("Incoming request to create user with phone number: {} and country code: {}", user.getContact().getPhoneNumber(), user.getContact().getCountryCode());
        ResponseEntity<ResponseModel<UserResponse>> response = ResponseEntity.ok(userService.createUser(user));
        log.info("User created with ID: {}", response.getBody().getData().getId());
        return response;
    }

    @Operation(summary = "Get user by ID", description = "Get the user details by their userID")
    @GetMapping("/getById/{userId}")
    public ResponseEntity<ResponseModel<UserResponse>> getUserById(@PathVariable(name = "userId") UUID userId){
        log.info("Incoming request to get user by ID: {}", userId);
        ResponseEntity<ResponseModel<UserResponse>> response = ResponseEntity.ok(userService.getUserById(userId));
        log.info("Fetched user with ID: {}", userId);
        return response;
    }

    @Operation(summary = "Get user by phone", description = "Get the user details by their phone number")
    @GetMapping("/getByPhoneNumber")
    public ResponseEntity<ResponseModel<UserResponse>> getUserByPhoneNumberAndCountryCode(
            @RequestParam Long phoneNumber,
            @RequestParam CountryCode countryCode){
        log.info("Incoming request to get user by phone number: {} and country code: {}", phoneNumber, countryCode);
        ResponseEntity<ResponseModel<UserResponse>> response = ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber, countryCode));
        log.info("Fetched user with phone number: {} and country code: {}", phoneNumber, countryCode);
        return response;
    }

    @Operation(summary = "Delete user by ID", description = "Delete the user record from DB by their userID")
    @DeleteMapping("/deleteById/{userId}")
    public ResponseEntity<ResponseModel<?>> deleteUserById(@PathVariable UUID userId){
        log.info("Incoming request to delete user by ID: {}", userId);
        ResponseEntity<ResponseModel<?>> response = ResponseEntity.ok(userService.deleteUserById(userId));
        log.info("Deleted user with ID: {}", userId);
        return response;
    }

    @Operation(summary = "Delete user by phone", description = "Delete the user record from DB by their phone number")
    @DeleteMapping("/deleteByPhoneNumber")
    public ResponseEntity<ResponseModel<?>> deleteUserByPhoneNumberAndCountryCode(@RequestParam Long phoneNumber, @RequestParam CountryCode countryCode){
        log.info("Incoming request to delete user by phone number: {} and country code: {}", phoneNumber, countryCode);
        ResponseEntity<ResponseModel<?>> response = ResponseEntity.ok(userService.deleteUserByPhoneNumber(phoneNumber, countryCode));
        log.info("Deleted user with phone number: {} and country code: {}", phoneNumber, countryCode);
        return response;
    }
}
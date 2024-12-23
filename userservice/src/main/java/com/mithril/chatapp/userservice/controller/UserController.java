package com.mithril.chatapp.userservice.controller;

import com.mithril.chatapp.userservice.dto.ResponseModel;
import com.mithril.chatapp.userservice.entity.Users;
import com.mithril.chatapp.userservice.service.UserService;
import com.mithril.chatapp.userservice.enums.CountryCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Tag(name = "User Controller", description = "API for managing users")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create user", description = "Create a new user using this POST endpoint")
    @PostMapping("/create")
    public ResponseEntity<ResponseModel<Users>> createUser(@RequestBody Users users){
        return ResponseEntity.ok(userService.createUser(users));
    }

    @Operation(summary = "Get user by phone", description = "Get the user details by their phone number")
    @GetMapping("/getByPhoneNumber")
    public ResponseEntity<ResponseModel<Users>> getUserByPhoneNumberAndCountryCode(
            @RequestParam Long phoneNumber,
            @RequestParam CountryCode countryCode){
        return ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber,countryCode));
    }

    @Operation(summary = "Get user by ID", description = "Get the user details by their userID")
    @GetMapping("/getById")
    public ResponseEntity<ResponseModel<Users>> getUserById(@RequestParam UUID id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Delete user by phone", description = "Delete the user record from DB by their phone number")
    @DeleteMapping("/deleteByPhoneNumber")
    public ResponseEntity<ResponseModel<?>> deleteUserByPhoneNumberAndCountryCode(@RequestParam Long phoneNumber, @RequestParam CountryCode countryCode){
        return ResponseEntity.ok(userService.deleteUserByPhoneNumber(phoneNumber,countryCode));
    }

    @Operation(summary = "Delete user by ID", description = "Delete the user record from DB by their userID")
    @DeleteMapping("/deleteById")
    public ResponseEntity<ResponseModel<?>> deleteUserById(@RequestParam UUID id){
        return ResponseEntity.ok(userService.deleteUserById(id));
    }
}

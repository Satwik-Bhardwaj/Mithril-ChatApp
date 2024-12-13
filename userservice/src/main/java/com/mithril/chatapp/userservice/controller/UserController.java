package com.mithril.chatapp.userservice.controller;

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
    public ResponseEntity<?>createUser(@RequestBody Users users){
        return userService.createUser(users);
    }

    @Operation(summary = "Get user by phone", description = "Get the user details by their phone number")
    @GetMapping("/getByPhoneNumber")
    public Users getUserByPhoneNumberAndCountryCode(
            @RequestParam Long phoneNumber,
            @RequestParam CountryCode countryCode){
        return userService.getUserByPhoneNumber(phoneNumber,countryCode);
    }

    @Operation(summary = "Get user by ID", description = "Get the user details by their userID")
    @GetMapping("/getById")
    public Users getUserById(@RequestParam UUID id){
        return userService.getUserById(id);
    }

    @Operation(summary = "Delete user by phone", description = "Delete the user record from DB by their phone number")
    @DeleteMapping("/deleteByPhoneNumber")
    public ResponseEntity<Void> deleteUserByPhoneNumberAndCountryCode(@RequestParam Long phoneNumber, @RequestParam CountryCode countryCode){
        return userService.deleteUserByPhoneNumber(phoneNumber,countryCode);
    }

    @Operation(summary = "Delete user by ID", description = "Delete the user record from DB by their userID")
    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteUserById(@RequestParam UUID id){
        return userService.deleteUserById(id);
    }
}

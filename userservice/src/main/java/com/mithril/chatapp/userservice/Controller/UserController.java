package com.mithril.chatapp.userservice.Controller;
import com.mithril.chatapp.userservice.Entity.Users;
import com.mithril.chatapp.userservice.Service.UserService;
import com.mithril.chatapp.userservice.enums.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity<?>createUser(@RequestBody Users users){
        return userService.createUser(users);
    }
    @GetMapping("/getByPhoneNumber")
    public Users getUserByPhoneNumberAndCountryCode(
            @RequestParam Long phoneNumber,
            @RequestParam CountryCode countryCode){
        return userService.getUserByPhoneNumber(phoneNumber,countryCode);
    }
    @GetMapping("/getById")
    public Users getUserById(@RequestParam UUID id){
        return userService.getUserById(id);
    }
    @DeleteMapping("/deleteByPhoneNumber")
    public ResponseEntity<Void> deleteUserByPhoneNumberAndCountryCode(@RequestParam Long phoneNumber, @RequestParam CountryCode countryCode){
        return userService.deleteUserByPhoneNumber(phoneNumber,countryCode);
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteUserById(@RequestParam UUID id){
        return userService.deleteUserById(id);
    }

}

package com.userservice.Controller;

import com.userservice.Entity.Users;
import com.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity<?>createUser(@RequestBody Users users){
        return userService.create(users);
    }
    @GetMapping("getUser/{username}")
    public ResponseEntity<?>getUserDetails(@PathVariable String username){
      return  userService.getUserDetails(username);
    }
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Void> DeleteUser(@PathVariable String username){
        return userService.deleteUser(username);
    }
}

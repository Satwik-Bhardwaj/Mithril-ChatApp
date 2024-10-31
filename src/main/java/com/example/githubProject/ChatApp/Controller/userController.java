package com.example.githubProject.ChatApp.Controller;

import com.example.githubProject.ChatApp.Entity.Users;
import com.example.githubProject.ChatApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class userController {
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

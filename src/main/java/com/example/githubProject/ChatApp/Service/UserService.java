package com.example.githubProject.ChatApp.Service;

import com.example.githubProject.ChatApp.Entity.Users;
import com.example.githubProject.ChatApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public ResponseEntity<?> create(Users users){
        Users usersSaved =userRepo.save(users);
        return ResponseEntity.ok(usersSaved);
    }
    public ResponseEntity<?> getUserDetails(String username) {
        Users users =userRepo.findByUsername(username);
        if(users!=null){
        return ResponseEntity.ok(users);
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<Void> deleteUser(String username){
        Users users =userRepo.findByUsername(username);
        if(users!=null){
       userRepo.delete(users);
       return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();

    }


}

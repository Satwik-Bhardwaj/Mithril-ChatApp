package com.userservice.Service;

import com.userservice.Entity.Users;
import com.userservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<?> create(Users users){
        Users usersSaved = userRepository.save(users);
        return ResponseEntity.ok(usersSaved);
    }
    public ResponseEntity<?> getUserDetails(String username) {
        Users users = userRepository.findByUsername(username);
        if(users!=null){
        return ResponseEntity.ok(users);
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<Void> deleteUser(String username){
        Users users = userRepository.findByUsername(username);
        if(users!=null){
       userRepository.delete(users);
       return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();

    }


}

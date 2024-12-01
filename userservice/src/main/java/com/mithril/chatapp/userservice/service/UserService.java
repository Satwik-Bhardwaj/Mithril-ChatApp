package com.mithril.chatapp.userservice.service;
import com.mithril.chatapp.userservice.enums.CountryCode;
import com.mithril.chatapp.userservice.entity.Users;
import com.mithril.chatapp.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users getUserByPhoneNumber(Long phoneNumber, CountryCode countryCode){
        Optional<Users> userDetails = userRepository.findByPhoneNumberAndCountryCode(phoneNumber,countryCode);
        if(userDetails.isPresent()){
            return userDetails.get();
        }
        else{
            throw new RuntimeException("User Not Found");
        }
    }

    public Users getUserById(UUID id){
        Optional<Users> userDetails = userRepository.findById(id);
        if(userDetails.isPresent()){
            return userDetails.get();
        }
        else{
            throw new RuntimeException("User Not Found");
        }
    }

    public ResponseEntity<?> createUser(Users users){
        Users usersSaved = userRepository.save(users);
        return ResponseEntity.ok(usersSaved);
    }

    public ResponseEntity<Void> deleteUserByPhoneNumber(Long phoneNumber,CountryCode countryCode){
        Users usersDetails = getUserByPhoneNumber(phoneNumber,countryCode);
            userRepository.delete(usersDetails);
            return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> deleteUserById(UUID id) {
        Users userDetails = getUserById(id);
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

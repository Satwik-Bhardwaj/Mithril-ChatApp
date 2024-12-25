package com.mithril.chatapp.userservice.service;
import com.mithril.chatapp.userservice.dto.ResponseModel;
import com.mithril.chatapp.userservice.enums.CountryCode;
import com.mithril.chatapp.userservice.entity.Users;
import com.mithril.chatapp.userservice.exception.UserAlreadyExistsException;
import com.mithril.chatapp.userservice.exception.UserNotFoundException;
import com.mithril.chatapp.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseModel<Users> getUserByPhoneNumber(Long phoneNumber, CountryCode countryCode){
        Optional<Users> userDetails = userRepository.findByPhoneNumberAndCountryCode(phoneNumber,countryCode);
        if(userDetails.isPresent()){
            return ResponseModel.success(
                    userDetails.get(),
                    HttpStatus.FOUND,
                    "User details fetched successfully!"
            );
        }
        else{
            throw new UserNotFoundException();
        }
    }

    public ResponseModel<Users> getUserById(UUID id){
        Optional<Users> userDetails = userRepository.findById(id);
        if(userDetails.isPresent()){
            return ResponseModel.success(
                    userDetails.get(),
                    HttpStatus.FOUND,
                    "User details fetched successfully!"
            );
        }
        else{
            throw new UserNotFoundException();
        }
    }

    public ResponseModel<Users> createUser(Users users){
        userRepository.findByPhoneNumberAndCountryCode(users.getPhoneNumber(),users.getCountryCode()).ifPresent(
            existingUser -> {
                throw new UserAlreadyExistsException();
        });

        Users usersSaved = userRepository.save(users);
        return ResponseModel.success(
                usersSaved,
                HttpStatus.CREATED,
                "User created successfully!"
        );
    }

    public ResponseModel<?> deleteUserByPhoneNumber(Long phoneNumber, CountryCode countryCode){
        Optional<Users> userDetails = userRepository.findByPhoneNumberAndCountryCode(phoneNumber, countryCode);
        if(userDetails.isPresent()) {
            userRepository.delete(userDetails.get());
        } else {
            throw new UserNotFoundException();
        }
        return ResponseModel.success(null, HttpStatus.OK, "User deleted successfully!");
    }

    public ResponseModel<?> deleteUserById(UUID id) {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
        return ResponseModel.success(null, HttpStatus.OK, "User deleted successfully!");
    }
}

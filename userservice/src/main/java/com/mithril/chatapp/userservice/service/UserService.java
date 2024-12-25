package com.mithril.chatapp.userservice.service;
import com.mithril.chatapp.userservice.dto.Contact;
import com.mithril.chatapp.userservice.dto.ResponseModel;
import com.mithril.chatapp.userservice.dto.request.UserRequest;
import com.mithril.chatapp.userservice.dto.response.UserResponse;
import com.mithril.chatapp.userservice.enums.CountryCode;
import com.mithril.chatapp.userservice.entity.Users;
import com.mithril.chatapp.userservice.enums.Status;
import com.mithril.chatapp.userservice.exception.UserAlreadyExistsException;
import com.mithril.chatapp.userservice.exception.UserNotFoundException;
import com.mithril.chatapp.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * This method is used to create a new user
     * @param user - the user details
     * @return - the response object
     */
    public ResponseModel<UserResponse> createUser(UserRequest user){
        // checking if the user already exists based on the contact details
        userRepository.findByPhoneNumberAndCountryCode(user.getContact().getPhoneNumber(), CountryCode.getByCode(user.getContact().getCountryCode())).ifPresent(
                existingUser -> {
                    throw new UserAlreadyExistsException();
                });

        // creating a new user entry
        Users users = new Users();
        users.setBio(user.getBio());
        users.setUsername(user.getUsername());
        users.setPhoneNumber(user.getContact().getPhoneNumber());
        users.setCountryCode(CountryCode.getByCode(user.getContact().getCountryCode()));
        users.setStatus(Status.ACTIVE);

        // saving the user
        Users savedUser = userRepository.save(users);

        // creating a response object
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setBio(savedUser.getBio());
        userResponse.setStatus(savedUser.getStatus());
        userResponse.setUsername(savedUser.getUsername());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setContact(new Contact(savedUser.getPhoneNumber(), savedUser.getCountryCode().toString()));

        // returning the response
        return ResponseModel.success(
                userResponse,
                HttpStatus.CREATED,
                "User created successfully!"
        );
    }

    /**
     * This method is used to get the user details by their ID
     * @param userId - the user ID
     * @return - the response object
     */
    public ResponseModel<UserResponse> getUserById(UUID userId) {
        // fetching the user details
        Users users = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        // preparing the response object
        UserResponse userResponse = new UserResponse();
        userResponse.setBio(users.getBio());
        userResponse.setUsername(users.getUsername());
        userResponse.setContact(new Contact(users.getPhoneNumber(), users.getCountryCode().getCode()));

        // returning the response
        return ResponseModel.success(
                userResponse,
                HttpStatus.FOUND,
                "User details fetched successfully!"
        );
    }

    /**
     * This method is used to get the user details by their phone number
     * @param phoneNumber - the phone number
     * @param countryCode - the country code
     * @return - the response object
     */
    public ResponseModel<UserResponse> getUserByPhoneNumber(Long phoneNumber, CountryCode countryCode){
        // fetching the user details
        Users users = userRepository.findByPhoneNumberAndCountryCode(phoneNumber,countryCode).orElseThrow(UserNotFoundException::new);

        // preparing the response object
        UserResponse userResponse = new UserResponse();
        userResponse.setBio(users.getBio());
        userResponse.setUsername(users.getUsername());
        userResponse.setContact(new Contact(users.getPhoneNumber(), users.getCountryCode().getCode()));

        // returning the response
        return ResponseModel.success(
                userResponse,
                HttpStatus.FOUND,
                "User details fetched successfully!"
        );
    }

    /**
     * This method is used to delete the user by their ID
     * @param userId - the user ID
     * @return - the response object
     */
    public ResponseModel<?> deleteUserById(UUID userId) {
        // checking if the user exists
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        // deleting the user
        userRepository.deleteById(userId);

        // returning the response
        return ResponseModel.success(null, HttpStatus.OK, "User deleted successfully!");
    }

    /**
     * This method is used to delete the user by their phone number
     * @param phoneNumber - the phone number
     * @param countryCode - the country code
     * @return - the response object
     */
    public ResponseModel<?> deleteUserByPhoneNumber(Long phoneNumber, CountryCode countryCode){
        // checking if the user exists
        Users users = userRepository.findByPhoneNumberAndCountryCode(phoneNumber, countryCode).orElseThrow(UserNotFoundException::new);

        // deleting the user
        userRepository.delete(users);

        // returning the response
        return ResponseModel.success(null, HttpStatus.OK, "User deleted successfully!");
    }
}

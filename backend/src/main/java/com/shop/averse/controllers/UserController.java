package com.shop.averse.controllers;

import com.shop.averse.database.models.User;
import com.shop.averse.database.repositories.UserRepository;
import com.shop.averse.services.UserService;
import com.shop.averse.util.StatusMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<StatusMessageResponse> createUser(@RequestBody User user) {
        StatusMessageResponse response = new StatusMessageResponse();

        if (userService.createUser(user)) {
            response.setMessage("User creation completed.");
            response.setStatus(true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.setMessage("User with the provided email already exists.");
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            updatedUser.setId(id);
            User savedUser = userRepository.save(updatedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusMessageResponse> deleteUser(@PathVariable Long id) {
        boolean deleteResult = userService.deleteUser(id);
        StatusMessageResponse response = new StatusMessageResponse();

        if (deleteResult) {
            response.setMessage("User deletion completed.");
            response.setStatus(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("User not found for deletion.");
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

package com.example.demo.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import com.example.demo.entities.UserClass;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  public static final String USER_NOT_FOUND_WITH_ID = "User not found with id:";
  private static final Logger log = LoggerFactory.getLogger(UserController.class);
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @GetMapping
  public ResponseEntity<List<UserClass>> getAllUsers() {
    log.info("Fetching all users");
    List<UserClass> users = userRepository.findAll();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserClass> getUserById(@PathVariable Long id) {
    log.info("Fetching user by id: {}", id);
    UserClass user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + " " + id));
    return ResponseEntity.ok(user);
  }

  @PostMapping
  public ResponseEntity<UserClass> createUser(@RequestBody UserClass user) {
    log.info("Creating a new user with username: {}", user.getUsername());
    if (user.getPassword().length() < 8 || !user.getPassword().matches(".*\\d.*")) {
      log.error("Password validation failed for user: {}", user.getUsername());
      return ResponseEntity.badRequest().body(null);
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    UserClass createdUser = userRepository.save(user);
    log.info("Created user with username: {}", user.getUsername());
    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserClass> updateUser(@PathVariable Long id, @RequestBody UserClass user) {
    log.info("Updating user with id: {}", id);
    return userRepository.findById(id)
        .map(existingUser -> {
          existingUser.setUsername(user.getUsername());
          existingUser.setEmail(user.getEmail());
          existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
          existingUser.setIsAdmin(user.getIsAdmin());
          log.info("Updated user with id: {}", id);
          return ResponseEntity.ok(userRepository.save(existingUser));
        })
        .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + " " + id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    log.info("Attempting to delete user with id: {}", id);
    userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + " " + id));
    userRepository.deleteById(id);
    log.info("Deleted user with id: {}", id);
    return ResponseEntity.noContent().build();
  }
}

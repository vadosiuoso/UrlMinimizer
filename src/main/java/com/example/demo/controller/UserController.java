package com.example.demo.controller;

import java.util.List;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
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
  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    log.info("Fetching all users");
    List<User> users = userService.findAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    log.info("Fetching user by id: {}", id);
    User user = userService.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + " " + id));
    return ResponseEntity.ok(user);
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody UserDto user) {
    log.info("Creating a new user with username: {}", user.getUsername());
    if (user.getPassword().length() < 8 || !user.getPassword().matches(".*\\d.*")) {
      log.error("Password validation failed for user: {}", user.getUsername());
      return ResponseEntity.badRequest().body(null);
    }
    User createdUser = userService.createOrUpdateUser(user);
    log.info("Created user with username: {}", user.getUsername());
    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto user) {
    log.info("Updating user with id: {}", id);
    userService.findById(id).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + " " + id));
    log.info("Updated user with id: {}", id);
    return ResponseEntity.ok(userService.createOrUpdateUser(user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    log.info("Attempting to delete user with id: {}", id);
    userService.findById(id).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + " " + id));
    userService.deleteUser(id);
    log.info("Deleted user with id: {}", id);
    return ResponseEntity.noContent().build();
  }
}

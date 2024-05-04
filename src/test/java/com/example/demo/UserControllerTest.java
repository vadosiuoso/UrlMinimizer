package com.example.demo;

import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.demo.controller.UserController;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.UserClass;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @Mock
  private UserService userService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserController userController;



  @Test
  public void testCreateUser() {
    UserDto user = new UserDto();
    user.setUsername("testUser");
    user.setEmail("test@example.com");
    user.setPassword("password123");
    UserClass userEntity = new UserClass();
    userEntity.set


    when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
    when(userService.createOrUpdateUser(user)).thenReturn(user);

    ResponseEntity<UserClass> response = userController.createUser(user);

    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Assertions.assertEquals(user, response.getBody());
  }

  @Test
  public void testDeleteUser() {
    Long userId = 1L;

    when(userService.findById(userId)).thenReturn(true);

    ResponseEntity<Void> response = userController.deleteUser(userId);

    Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(userRepository, times(1)).deleteById(userId);
  }

  @Test
  public void testGetUserById() {
    Long userId = 1L;
    UserClass user = new UserClass();
    user.setUserId(userId);
    user.setUsername("testUser");
    user.setEmail("test@example.com");
    user.setPassword("password123");
    user.setAdmin(false);

    when(userService.findById(userId)).thenReturn(Optional.of(user));

    ResponseEntity<UserClass> response = userController.getUserById(userId);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(user, response.getBody());
  }
}

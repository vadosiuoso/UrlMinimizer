package com.example.demo;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.example.demo.controller.UserController;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
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



//  @Test
//  public void testCreateUser() {
//    UserDto user = new UserDto();
//    user.setUsername("testUser");
//    user.setEmail("test@example.com");
//    user.setPassword("password123");
//    UserClass userEntity = new UserClass();
//    userEntity.set
//
//
//    when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
//    when(userService.createOrUpdateUser(user)).thenReturn(user);
//
//    ResponseEntity<UserClass> response = userController.createUser(user);
//
//    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
//    Assertions.assertEquals(user, response.getBody());
//  }

//  @Test
//  public void testDeleteUser() {
//    Long userId = 1L;
//
//    when(userService.findById(userId)).thenReturn(true);
//
//    ResponseEntity<Void> response = userController.deleteUser(userId);
//
//    Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//    verify(userRepository, times(1)).deleteById(userId);
//  }

  @Test
  public void testGetUserById() {
    Long userId = 1L;
    User user = new User();
    user.setId(userId);
    user.setUsername("testUser");
    user.setEmail("test@example.com");
    user.setPassword("password123");
    user.setRoles(List.of());

    when(userService.findById(userId)).thenReturn(Optional.of(user));

    ResponseEntity<User> response = userController.getUserById(userId);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(user, response.getBody());
  }
}

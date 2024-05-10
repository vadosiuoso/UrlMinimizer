package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
  Optional<User> findById(Long id);
  User findByUsername(String username);
  List<User> findAllUsers();
  void deleteUser(Long id);
  boolean isUserExist(Long id);
  User createOrUpdateUser(UserDto registrationUserDto);
  Optional<User> findByUsernameOrEmail(String usernameOrEmail);



}

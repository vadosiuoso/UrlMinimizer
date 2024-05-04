package com.example.demo.services;

import com.example.demo.dto.UserDto;
import com.example.demo.entities.UserClass;

import java.util.List;
import java.util.Optional;

public interface UserService {
  Optional<UserClass> findById(Long id);
  UserClass findByUsername(String username);
  List<UserClass> findAllUsers();
  void deleteUser(Long id);
  UserClass createOrUpdateUser(UserDto registrationUserDto);
  Optional<UserClass> findByUsernameOrEmail(String usernameOrEmail);



}

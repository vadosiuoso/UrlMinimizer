package com.example.demo.services;

import com.example.demo.dto.RegistrationUserDto;
import com.example.demo.entities.UserClass;

import java.util.List;
import java.util.Optional;

public interface UserService {
  Optional<UserClass> findById(Long id);
  UserClass findByUsername(String username);
  List<UserClass> findAllUsers();
  void deleteUser(Long id);
  UserClass createOrUpdateUser(RegistrationUserDto registrationUserDto);
  Optional<UserClass> findByUsernameOrEmail(String usernameOrEmail);



}

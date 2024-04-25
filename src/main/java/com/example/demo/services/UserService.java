package com.example.demo.services;

import com.example.demo.entities.UserClass;

import java.util.List;
import java.util.Optional;

public interface UserService {
  Optional<UserClass> findById(Long id);
  UserClass findByUsername(String username);
  List<UserClass> findAllUsers();
  void deleteUser(Long id);
  void createOrUpdateUser(UserClass userClass);
  Optional<UserClass> findByUsernameOrEmail(String usernameOrEmail);



}

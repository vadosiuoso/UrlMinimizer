package com.example.demo.services;


import com.example.demo.dto.UserDto;
import com.example.demo.entities.UserClass;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;
  @Override
  public Optional<UserClass> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public UserClass findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public List<UserClass> findAllUsers() {
    return userRepository.findAll();
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void createOrUpdateUser(UserClass user) {
    userRepository.save(user);
  }

  @Override
  public Optional<UserClass> findByUsernameOrEmail(String usernameOrEmail) {
    return userRepository.findByUsernameOrEmail(usernameOrEmail);
  }


}

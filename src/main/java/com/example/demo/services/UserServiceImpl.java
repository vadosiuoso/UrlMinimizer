package com.example.demo.services;


import com.example.demo.dto.RegistrationUserDto;
import com.example.demo.entities.UserClass;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

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
  public UserClass createOrUpdateUser(RegistrationUserDto registrationUserDto) {
    UserClass userClass = new UserClass();
    userClass.setUsername(registrationUserDto.getUsername());
    userClass.setEmail(registrationUserDto.getEmail());
    userClass.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
    return userRepository.save(userClass);
  }

  @Override
  public Optional<UserClass> findByUsernameOrEmail(String usernameOrEmail) {
    return userRepository.findByUsernameOrEmail(usernameOrEmail);
  }


}

package com.example.demo.service;


import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
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
  private final RoleRepository roleRepository;

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public boolean isUserExist(Long id) {
    Optional<User> user = findById(id);
    return user.isPresent();
  }

  @Override
  @Transactional
  public User createOrUpdateUser(UserDto registrationUserDto) {
    User user = new User();
    user.setUsername(registrationUserDto.getUsername());
    user.setEmail(registrationUserDto.getEmail());
    user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
    user.setRoles(List.of(roleRepository.findByName("ROLE_USER").get()));
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
    return userRepository.findByUsernameOrEmail(usernameOrEmail);
  }


}

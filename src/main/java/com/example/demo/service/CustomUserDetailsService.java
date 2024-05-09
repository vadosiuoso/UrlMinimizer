package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    log.info("Trying to authenticate user : {}", usernameOrEmail);
    User user = userRepository.findByUsernameOrEmail(usernameOrEmail)
        .orElseThrow(() -> {
          log.error("User not found : {}", usernameOrEmail);
          return new UsernameNotFoundException("User not found: " + usernameOrEmail);
        });

    log.info("User found : {}", user.getUsername());
    log.info("Email of user : {}", user.getEmail());
    return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
        .password(user.getPassword())
        .authorities(user.getRoles().stream().map(role -> new SimpleGrantedAuthority((role.getName()))).collect(Collectors.toList()))
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }
}

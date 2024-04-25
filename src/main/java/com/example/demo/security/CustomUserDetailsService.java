package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.demo.entities.UserClass;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    log.info("Trying to authenticate user : {}", usernameOrEmail);
    UserClass user = userRepository.findByUsernameOrEmail(usernameOrEmail)
        .orElseThrow(() -> {
          log.error("User not found : {}", usernameOrEmail);
          return new UsernameNotFoundException("User not found: " + usernameOrEmail);
        });

    log.info("User found : {}", user.getUsername());
    log.info("Email of user : {}", user.getEmail());
    return User.withUsername(user.getUsername())
        .password(user.getPassword())
        .authorities(user.getIsAdmin() ? "ADMIN" : "USER")
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }
}

package com.example.demo.controller;

import com.example.demo.dto.JwtRequest;
import com.example.demo.dto.JwtResponse;
import com.example.demo.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;

  public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @PostMapping("/login")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
    logger.info("Attempting to authenticate user: {}", authenticationRequest.getUsername());
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUsername(),
            authenticationRequest.getPassword()
        )
    );
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String token = jwtTokenUtil.generateToken(userDetails.getUsername());
    logger.info("Authentication successful for user: {}", userDetails.getUsername());
    return ResponseEntity.ok(new JwtResponse(token));
  }


  @GetMapping("/test")
  public String getName(Principal principal){
      return principal.getName();
  }
}

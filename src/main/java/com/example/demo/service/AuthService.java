package com.example.demo.service;

import com.example.demo.dto.JwtRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
  ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest);

}

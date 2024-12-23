package com.example.demo.controller;

import com.example.demo.dto.AppError;
import com.example.demo.dto.JwtRequest;
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final AuthService authService;


  @PostMapping("/login")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
    return authService.createAuthenticationToken(authenticationRequest);
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto registrationUserDto) {
    User user = userService.createOrUpdateUser(registrationUserDto);
    return ResponseEntity.ok(user);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    StringBuilder errorMessage = new StringBuilder("Validation error(s): ");
    for (FieldError fieldError : result.getFieldErrors()) {
      errorMessage.append(fieldError.getDefaultMessage()).append(", ");
    }
    return ResponseEntity.badRequest().body(errorMessage.toString());
  }

  @GetMapping("/test")
  public String getName(Principal principal){
      return principal.getName();
  }
}

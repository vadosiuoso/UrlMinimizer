package com.example.demo.service;
import com.example.demo.dto.AppError;
import com.example.demo.dto.JwtRequest;
import com.example.demo.dto.JwtResponse;
import com.example.demo.util.JwtTokenUtil;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

  private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;

  @Override
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
    logger.info("Attempting to authenticate user: {}", authenticationRequest.getUsername());
    Authentication authentication;
    try {
      authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authenticationRequest.getUsername(),
              authenticationRequest.getPassword()
          )
      );
    }catch (BadCredentialsException e){
      logger.debug("Authentication failed");
      return new ResponseEntity<>(
          new AppError(HttpStatus.UNAUTHORIZED.value(), "Wrong username or password"),
          HttpStatus.UNAUTHORIZED
      );
    }
    assert authentication != null;
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String token = jwtTokenUtil.generateToken(userDetails.getUsername());
    logger.info("Authentication successful for user: {}", userDetails.getUsername());
    return ResponseEntity.ok(new JwtResponse(token));
  }
}

package com.example.demo.anotation;

import com.example.demo.entities.UserClass;
import com.example.demo.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

  @Autowired
  private final UserService userService;
  @Override
  public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
      UserClass user = userService.findByUsername(username);
      return user == null;
  }
}

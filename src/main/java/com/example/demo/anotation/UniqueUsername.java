package com.example.demo.anotation;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsername.UniqueUsernameValidator.class)
@Documented
public @interface UniqueUsername {
  String message() default "This username is already taken";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};


  @RequiredArgsConstructor
  class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private final UserService userService;
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        User user = userService.findByUsername(username);
        return user == null;
    }
  }
}

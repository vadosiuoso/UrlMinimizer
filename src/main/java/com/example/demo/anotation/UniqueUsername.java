package com.example.demo.anotation;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Documented
public @interface UniqueUsername {
  String message() default "This username is already taken";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

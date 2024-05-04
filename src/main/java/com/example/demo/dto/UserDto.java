package com.example.demo.dto;


import com.example.demo.anotation.UniqueUsername;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class UserDto {
  @UniqueUsername()
  private String username;
  private String email;
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
      message = "Password must contain at least 8 characters, including digits, uppercase, and lowercase letters.")
  private String password;
  private String confirmPassword;

  @AssertTrue(message =
      "This password and confirmation password must match.")
  private boolean isValid() {
    return password != null && password.equals(confirmPassword);
  }
}

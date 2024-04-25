package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserClass {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "is_admin", nullable = false)
  private boolean isAdmin;

  public boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }
}

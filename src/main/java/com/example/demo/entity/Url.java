package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "links")
public class Url {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(name = "original_url", nullable = false)
  private String originalUrl;

  @Column(name = "short_url", nullable = false)
  private String shortUrl;

  @Column(name = "calls")
  private Integer countOfCalls;

  @Column(name = "creation_date")
  private LocalDateTime creationDate;

  public Url() {
    this.countOfCalls = 0;
    this.creationDate = LocalDateTime.now();
  }
}

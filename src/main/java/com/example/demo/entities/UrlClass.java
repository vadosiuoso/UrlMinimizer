package com.example.demo.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "links")
public class UrlClass {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "link_id")
  private Long linkId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private UserClass user;

  @Column(name = "original_url", nullable = false)
  private String originalUrl;

  @Column(name = "short_url", nullable = false)
  private String shortUrl;

  @Column(name = "calls")
  private Integer countOfCalls;

  @Column(name = "creation_date")
  private LocalDateTime creationDate;

  public UrlClass() {
    this.countOfCalls = 0;
    this.creationDate = LocalDateTime.now();
  }
}

package com.example.demo.repository;

import java.util.*;
import java.util.Optional;
import com.example.demo.entity.Url;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
  Optional<Url> findByShortUrl(String shortUrl);

  List<Url> findAllByUser(User user);

  void deleteById(Long id);




}

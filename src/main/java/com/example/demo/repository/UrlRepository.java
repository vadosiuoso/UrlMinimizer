package com.example.demo.repository;

import java.util.*;
import java.util.Optional;
import com.example.demo.entities.UrlClass;

import com.example.demo.entities.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<UrlClass, Long> {
  Optional<UrlClass> findByShortUrl(String shortUrl);

  List<UrlClass> findAllByUser(UserClass user);

  Optional<UrlClass> deleteByLinkId(Long id);




}

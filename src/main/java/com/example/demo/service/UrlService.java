package com.example.demo.service;

import com.example.demo.dto.AppError;
import com.example.demo.dto.UrlRequest;
import com.example.demo.entity.Url;
import com.example.demo.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UrlService {
    List<Url> findAllByUser(User user);
    void deleteUrlById(Long id);
    Url createOrUpdate(Url url);
    Optional<Url> findById(Long id);
    Optional<Url> findByShortUrl(String shortUrl);
    ResponseEntity<?> deleteUrl(@PathVariable Long id, Principal principal);
    ResponseEntity<?> createUrl(@RequestBody UrlRequest url, Principal principal);
}

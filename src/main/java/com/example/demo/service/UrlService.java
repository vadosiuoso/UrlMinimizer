package com.example.demo.service;

import com.example.demo.entity.Url;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UrlService {
    List<Url> findAllByUser(User user);
    void deleteUrlById(Long id);
    Url createOrUpdate(Url url);
    Optional<Url> findById(Long id);
    Optional<Url> findByShortUrl(String shortUrl);
}

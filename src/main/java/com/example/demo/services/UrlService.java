package com.example.demo.services;

import com.example.demo.entities.UrlClass;
import com.example.demo.entities.UserClass;

import java.util.List;
import java.util.Optional;

public interface UrlService {
    List<UrlClass> findAllByUser(UserClass userClass);
    Optional<UrlClass> deleteUrlById(Long id);
    UrlClass createOrUpdate(UrlClass urlClass);
    Optional<UrlClass> findById(Long id);
    Optional<UrlClass> findByShortUrl(String shortUrl);
}

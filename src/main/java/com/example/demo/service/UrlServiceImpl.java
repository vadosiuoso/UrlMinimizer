package com.example.demo.service;


import com.example.demo.controller.UrlController;
import com.example.demo.dto.AppError;
import com.example.demo.dto.UrlRequest;
import com.example.demo.entity.Url;
import com.example.demo.entity.User;
import com.example.demo.repository.UrlRepository;
import jakarta.persistence.Temporal;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService{

  private final UrlRepository urlRepository;
  private final UserService userService;
  private final UrlShortenerService shortenerService;
  private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);


  @Override
  public List<Url> findAllByUser(User user) {
    return urlRepository.findAllByUser(user);
  }

  @Override
  @Transactional
  public void deleteUrlById(Long id) {
    urlRepository.deleteById(id);
  }

  @Override
  @Transactional
  public Url createOrUpdate(Url url) {
     return urlRepository.save(url);
  }

  @Override
  public Optional<Url> findById(Long id) {
    return urlRepository.findById(id);
  }

  @Override
  public Optional<Url> findByShortUrl(String shortUrl) {
    return urlRepository.findByShortUrl(shortUrl);
  }

  @Override
  @Transactional
  public ResponseEntity<?> deleteUrl(@PathVariable Long id, Principal principal) {
    log.info("Deleting URL with id: {}", id);
    Optional<Url> optUrl = findById(id);
    if(optUrl.isPresent()){
      Url url = optUrl.get();
      User user = userService.findByUsername(principal.getName());
      if(url.getUser().getId().equals(user.getId())) {
        deleteUrlById(url.getId());
        return ResponseEntity.ok(url);
      }else{
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
      }
    }
    return new ResponseEntity<>(
        new AppError(
            HttpStatus.BAD_REQUEST.value(),
            String.format("URL with id: %s not found", id)), HttpStatus.BAD_REQUEST);
  }


  @Override
  @Transactional
  public ResponseEntity<?> createUrl(@RequestBody UrlRequest url, Principal principal) {
    String originalUrl = url.getUrl();
    log.info(String.format("%s is trying to create a new short URL", principal.getName()));
    User user = userService.findByUsername(principal.getName());
    log.info(String.format("Trying to save URL %s in the database", originalUrl));

    if(isValidURL(originalUrl)){
      String shortUrl = shortenerService.shortenerUrl(originalUrl);
      Url urlClass = new Url();
      urlClass.setId(0L);
      urlClass.setUser(user);
      urlClass.setOriginalUrl(originalUrl);
      urlClass.setShortUrl(shortUrl);
      log.info("Creating new URL for original URL: {}", urlClass.getOriginalUrl());
      Url created = createOrUpdate(urlClass);
      return ResponseEntity.ok(created);
    } else {
      return new ResponseEntity<>(
          new AppError(HttpStatus.OK.value(), "Current URl is not responding"),
          HttpStatus.OK);
    }
  }

  private static boolean isValidURL(String url) {
    try {
      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      con.setRequestMethod("GET");
      int responseCode = con.getResponseCode();
      return (responseCode >= 200 && responseCode < 400);
    } catch (IOException e) {
      return false;
    }
  }
}

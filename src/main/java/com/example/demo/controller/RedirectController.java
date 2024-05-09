package com.example.demo.controller;

import com.example.demo.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import com.example.demo.entity.Url;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedirectController {

  private final UrlService urlService;
  private final Logger log = LoggerFactory.getLogger(RedirectController.class);


  @GetMapping("/{shortUrl:[a-zA-Z0-9]{6,8}}")
  public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response) {
    log.info("Searching for short URL: {}", shortUrl);
    Optional<Url> url = urlService.findByShortUrl(shortUrl);
    if(url.isPresent()){
      Url urlClass = url.get();
      urlClass.setCountOfCalls(urlClass.getCountOfCalls() + 1);
      urlService.createOrUpdate(urlClass);
      try{
        response.sendRedirect(urlClass.getOriginalUrl());
        log.info("Redirect attempt for short URL: {}", shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND).build();
      } catch (IOException e) {
        log.error("Failed to redirect short URL: {}", shortUrl, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    } else {
      return ResponseEntity.badRequest().body("Url is not available");
    }
  }

}
